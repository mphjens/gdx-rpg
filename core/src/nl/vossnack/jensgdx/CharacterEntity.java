/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx;

import nl.vossnack.jensgdx.World.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Shape;
import java.util.List;
import nl.vossnack.jensgdx.Constants.Direction;

/**
 *
 * @author Jens
 */
public abstract class CharacterEntity extends PhysicsEntity{
    
    protected CharacterStats stats;
    
    Vector2 velocity;
    Vector2 direction;
    protected boolean canMove = true;
    protected boolean drawHealthBar = true;
    
    protected Direction facingDirection;
    
    CharacterWeapon currentWeapon;
     
    public CharacterEntity(){
        super();        
    }
    
    public void setUp(String baseImagePath, List<SpriteAnimationInfo> animations, int spritesheetPadding, Shape colShape, CharacterStats stats){
        super.setUp(colShape, BodyType.DynamicBody, 
                    Constants.CollisionLayer.CATEGORY_FRIENDLY, 
                    Constants.CollisionLayer.createMask(Constants.CollisionLayer.CATEGORY_ENEMY.getCode(), 
                                                        Constants.CollisionLayer.CATEGORY_BOUNDS.getCode(), 
                                                        Constants.CollisionLayer.CATEGORY_OBSTACLE.getCode()), 
                    baseImagePath, animations, spritesheetPadding);
                
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
        this.bd.linearDamping = 35.0f;
    }
    
    @Override
    public void addToWorld(GameWorld world){
        super.addToWorld(world);
        this.body.setSleepingAllowed(false);
    }
            
    @Override
    public void update(float deltatime){
        
        if(this.canMove){
            velocity.add(direction.x, direction.y);

            if(direction.x == 0f){
                velocity.x = 0;
            }
            if(direction.y == 0f){
                velocity.y = 0;
            }

            velocity.clamp(-stats.speed, stats.speed);
            //System.out.println(velocity + " " + this.body.getPosition() + " " + this.getSprite().getPosition());
            body.applyLinearImpulse(velocity, this.body.getPosition(), true);
            
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
        
    public void move(Vector2 dir){
        this.direction = dir;
        if(dir.x == 0 && dir.y == 0)
        {
            this.getSprite(0).pauseAnimation();
            this.getSprite(0).showFrame(0);
        } else{
            if(dir.y > 0){
                facingDirection = Direction.NORTH;
                this.getSprite(0).setBaseAnimation("walk-up");
            }else
            if(dir.y < 0){
                facingDirection = Direction.SOUTH;
                this.getSprite(0).setBaseAnimation("walk-down");
            }else
            if(dir.x > 0){
                facingDirection = Direction.EAST;
                this.getSprite(0).setBaseAnimation("walk-right");
            }else
            if(dir.x < 0){
                facingDirection = Direction.WEST;
                this.getSprite(0).setBaseAnimation("walk-left");
            }
            
            
        }
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
            this.getSprite(0).resumeAnimation();
        else
            this.getSprite(0).pauseAnimation();
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
