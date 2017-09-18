/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx;

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
public abstract class CharacterEntity extends PhysicsEntity{
    
    protected CharacterStats stats;
    
    float timer = 0;
    
    Vector2 velocity;
    Vector2 direction;
    protected boolean canMove = true;
    protected boolean drawHealthBar = true;
    
    public AnimatedSprite bodySprite;
    
    protected Direction facingDirection;
    
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
    }
     
    public CharacterEntity(CharacterStats stats){
        this.stats = stats;
        this.velocity = new Vector2();
        this.direction = new Vector2();  
        this.facingDirection = Direction.SOUTH;
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
        
        
        if(this.currentPath != null && this.canMove){
            timer += deltatime;
            float ratio = (timer - this.lastNodeTime) / (new Vector2(cNodePos.x - lastNodePos.x, cNodePos.y - lastNodePos.y).len() / this.stats.speed);

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
                velocity.set(direction.nor().scl(stats.speed / Constants.PHYSICS_SCALE));
                body.setTransform(this.body.getPosition().add(velocity.scl(deltatime)), 0);
            }
        }
        
        if(this.currentWeapon != null)
            this.currentWeapon.update(deltatime);
        
        super.update(deltatime);
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
                this.bodySprite.setBaseAnimation("walk-up");
                break;
            case SOUTH:
                facingDirection = Direction.SOUTH;
                this.bodySprite.setBaseAnimation("walk-down");
                break;
            case EAST:
                facingDirection = Direction.EAST;
                this.bodySprite.setBaseAnimation("walk-right");
                break;
            case WEST:
                facingDirection = Direction.WEST;
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
            this.bodySprite.pauseAnimation();
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
        this.lastNodePos = this.getPosition();
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
        else
            this.bodySprite.pauseAnimation();
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
    
}
