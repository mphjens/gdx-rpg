/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx;

import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import nl.vossnack.jensgdx.World.GameWorld;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Shape;
import java.awt.Point;
import java.util.List;
import nl.vossnack.jensgdx.Constants.Direction;
import nl.vossnack.jensgdx.World.PathFinderNode;

/**
 *
 * @author Jens
 */
public class CharacterEntity extends PhysicsEntity implements Steerable<Vector2>{
    
    protected CharacterStats stats;
    
    float timer = 0;
    
    Vector2 velocity;
    Vector2 direction;
    protected boolean canMove = true;
    protected boolean drawHealthBar = true;
    
    public AnimatedSprite bodySprite;
    
    private static final SteeringAcceleration<Vector2> steeringOutput = 
		new SteeringAcceleration<Vector2>(new Vector2());
    public SteeringBehavior<Vector2> steeringBehavior;
    Location<Vector2> newLocation;
    Vector2 linearVelocity;
    float steeringOrientation;
    float angularVelocity;
    float maxSpeed;
    float maxAccel;
    
    protected Direction facingDirection;
    protected Vector2 forward; 
    
    CharacterWeapon currentWeapon;
    
    public boolean shouldPatrol;
    private boolean onpatrolwayback;
    private int cPathIndex;
    private List<PathFinderNode> currentPath;
    Vector2 cNodePos;
    Vector2 lastNodePos;
    private float lastNodeTime = 0;
     
    public CharacterEntity(){
        this.stats = new CharacterStats();
        this.velocity = new Vector2();
        this.direction = new Vector2();  
        this.facingDirection = Direction.SOUTH;
        this.linearVelocity = new Vector2();
        this.forward = new Vector2();
    }
     
    public CharacterEntity(CharacterStats stats){
        this.stats = stats;
        this.velocity = new Vector2();
        this.direction = new Vector2();  
        this.facingDirection = Direction.SOUTH;
        this.linearVelocity = new Vector2();
    }
    
    public void takeHit(int damage){
        
    }    
    
    @Override
    public void loadEntity(){
        super.loadEntity();
        if(bodySprite != null){
            bodySprite.loadAnimations();
        }
    
        this.bd.linearDamping = 35.0f;
    }
    
    @Override
    public void addToWorld(GameWorld world){
        super.addToWorld(world);
        this.body.setSleepingAllowed(false);
    }
            
    @Override
    public void update(float deltatime){
        
        if (steeringBehavior != null) {
            // Calculate steering acceleration
            steeringBehavior.calculateSteering(steeringOutput);
            
            /*
             * Here you might want to add a motor control layer filtering steering accelerations.
             * 
             * For instance, a car in a driving game has physical constraints on its movement:
             * - it cannot turn while stationary
             * - the faster it moves, the slower it can turn (without going into a skid)
             * - it can brake much more quickly than it can accelerate
             * - it only moves in the direction it is facing (ignoring power slides)
             */

            // Apply steering acceleration to move this agent
            applySteering(steeringOutput, deltatime);
        }
        if(this.currentPath != null && this.canMove){
            timer += deltatime;
            float ratio = (timer - this.lastNodeTime) / (new Vector2(cNodePos.x - lastNodePos.x, cNodePos.y - lastNodePos.y).len() / (this.stats.speed * Constants.PHYSICS_SCALE) );

            if(ratio >= 1f){                
                if( this.cPathIndex == this.currentPath.size() - 1){
                    if(this.shouldPatrol)
                        this.onpatrolwayback = true;
                    else{
                        this.cancelPath();
                        return;
                    }
                }else if(this.shouldPatrol && this.cPathIndex == 0){
                    this.onpatrolwayback = false;
                }
                
                
                if(!this.onpatrolwayback)
                    this.cPathIndex++;
                else
                    this.cPathIndex--;
                
                this.lastNodePos = this.cNodePos;
                this.cNodePos = Constants.tileToWorldSpace(currentPath.get(cPathIndex).x, currentPath.get(cPathIndex).y).add(Constants.TILE_SIZE / 2f, 4f);
                this.lastNodeTime = timer;
                this.move(new Vector2(this.cNodePos).sub(lastNodePos));
                ratio = ratio - 1f;
            }
            
            body.setTransform((lastNodePos.x + ((cNodePos.x - lastNodePos.x) * ratio)) / Constants.PHYSICS_SCALE, (lastNodePos.y + ((cNodePos.y - lastNodePos.y) * ratio)) / Constants.PHYSICS_SCALE, 0);
        }
        else{
            if(this.canMove){
                
                if(this.steeringBehavior != null){
                    velocity.set(this.linearVelocity);
                }else{
                    velocity.set(direction.nor().scl(stats.speed));
                }
                this.setFacingDirection(direction);
                
                body.setTransform(this.body.getPosition().add(velocity.scl(deltatime)), 0);
            }
        }
        
        if(this.currentWeapon != null)
            this.currentWeapon.update(deltatime);
        
        super.update(deltatime);
    }
    
    private void applySteering (SteeringAcceleration<Vector2> steering, float time) {
        // Update position and linear velocity. Velocity is trimmed to maximum speed
        //body.setTransform(this.body.getPosition().add(linearVelocity.scl(time*Constants.PHYSICS_SCALE)), 0);
        this.linearVelocity.mulAdd(steering.linear, time).limit(this.getMaxLinearSpeed());
        this.direction.set(this.linearVelocity);
        //this.setFacingDirection(direction);
        // Update orientation and angular velocity
        // For non-independent facing we have to align orientation to linear velocity
        float newOrientation = this.vectorToAngle(this.linearVelocity);
        if (newOrientation != this.getOrientation()) {
                this.angularVelocity = (newOrientation - this.getOrientation()) * time;
                this.setOrientation(newOrientation);
        }
        
        

    }
    
    public void equipWeapon(CharacterWeapon weapon){
        this.currentWeapon = weapon;
        weapon.onEquip(this);
    }
    
    public void fireWeapon(int firemode){
        if(this.currentWeapon != null){
            currentWeapon.fire(firemode);
        }
            
    }
    
    public void setFacingDirection(Direction dir){
        if(facingDirection != dir){
            switch(dir){
            case NORTH:
                facingDirection = Direction.NORTH;
                forward.set(0, 1);
                this.bodySprite.setBaseAnimation("walk-up");
                break;
            case SOUTH:
                facingDirection = Direction.SOUTH;
                forward.set(0, -1);
                this.bodySprite.setBaseAnimation("walk-down");
                break;
            case EAST:
                facingDirection = Direction.EAST;
                forward.set(1, 0);
                this.bodySprite.setBaseAnimation("walk-right");
                break;
            case WEST:
                facingDirection = Direction.WEST;
                forward.set(-1, 0);
                this.bodySprite.setBaseAnimation("walk-left");
                break;
            }
        }
    }
    
     public void setFacingDirection(Vector2 dir){
            if(dir.x > 0){
                setFacingDirection(Direction.EAST);
            }else
            if(dir.x < 0){
                setFacingDirection(Direction.WEST);
            }else if(dir.y > 0){
                setFacingDirection(Direction.NORTH);
            }else
            if(dir.y < 0){
                setFacingDirection(Direction.SOUTH);
            }
     }
        
    public void move(Vector2 dir){
        this.direction = dir;
        if(dir.x == 0 && dir.y == 0 && this.currentPath == null)
        {
            //this.bodySprite.pauseAnimation();
            if(this.bodySprite.currentanimationInfo == this.bodySprite.baseAnimInfo)
                this.bodySprite.showFrame(0);
        } else{
            setFacingDirection(dir);
        }
    }
    
    public void setCurrentPath(List<PathFinderNode> path, boolean patrol){
        this.currentPath = path;
        this.shouldPatrol = patrol;
        this.onpatrolwayback = false;
        this.cPathIndex = 0;
        if(currentPath.size() > 1)
            this.cPathIndex = 1;
        this.lastNodeTime = timer;
        this.cNodePos = Constants.tileToWorldSpace(currentPath.get(cPathIndex).x, currentPath.get(cPathIndex).y).add(Constants.TILE_SIZE / 2f, 4f);
        this.lastNodePos = this.getLocation();
        this.move(new Vector2(this.cNodePos).sub(lastNodePos));
    }
    
    public void cancelPath(){
        this.currentPath = null;
    }

    /**
     * @return the facingDirection
     */
    public Direction getFacingDirection() {
        return facingDirection;
    }

    /**
     * @return the canMove
     */
    public boolean canMove() {
        return canMove;
    }

    /**
     * @param canMove the canMove to set
     */
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
        if(canMove)
            this.bodySprite.resumeAnimation();
        //else
            //this.bodySprite.pauseAnimation();
    }

    @Override
    public void onContactBegin(PhysicsEntity other, Contact contact) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onContactEnd(PhysicsEntity other, Contact contact) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the stats
     */
    public CharacterStats getStats() {
        return stats;
    }

    /**
     * @param stats the stats to set
     */
    public void setStats(CharacterStats stats) {
        this.stats = stats;
    }

    /**
     * @return the drawHealthBar
     */
    public boolean shouldDrawHealthBar() {
        return drawHealthBar;
    }

    /**
     * @param drawHealthBar the drawHealthBar to set
     */
    public void setDrawHealthBar(boolean drawHealthBar) {
        this.drawHealthBar = drawHealthBar;
    }
    
    // Actual implementation depends on your coordinate system.
    // Here we assume the y-axis is pointing upwards.
    @Override
    public float vectorToAngle (Vector2 vector) {
            return (float)Math.atan2(-vector.x, vector.y);
    }

    // Actual implementation depends on your coordinate system.
    // Here we assume the y-axis is pointing upwards.
    @Override
    public Vector2 angleToVector (Vector2 outVector, float angle) {
            outVector.x = -(float)Math.sin(angle);
            outVector.y = (float)Math.cos(angle);
            return outVector;
    }

    @Override
    public Vector2 getLinearVelocity() {
        return this.linearVelocity;
    }

    @Override
    public float getAngularVelocity() {
        return 0;
    }

    @Override
    public float getBoundingRadius() {
        return this.body.getFixtureList().get(0).getShape().getRadius();
    }

    @Override
    public boolean isTagged() {
        return false;//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTagged(boolean tagged) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float getOrientation() {
        return this.steeringOrientation; //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setOrientation(float orientation) {
        this.steeringOrientation = orientation;
    }

    @Override
    public Location<Vector2> newLocation() {
        return newLocation;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0.08f;//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float getMaxLinearSpeed() {
        return this.maxSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return this.maxAccel;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxAccel = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return 1000;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float getMaxAngularAcceleration() {
        return 1000;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vector2 getPosition() {
        return this.body.getPosition();
    }

    /**
     * @return the forward
     */
    public Vector2 getForward() {
        return forward;
    }
    
}
