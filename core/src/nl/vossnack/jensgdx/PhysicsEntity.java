/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx;

import nl.vossnack.jensgdx.World.GameWorld;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Shape;
import java.util.List;


/**
 *
 * @author Jens
 */
public abstract class PhysicsEntity extends Entity{
    protected BodyDef bd;
    protected BodyDef.BodyType bodyType;
    protected Constants.CollisionLayer collisionCategory; //Short. Which category does this entity belong to. Should be one of Constants.CollisionLayers properties
    protected short collisionMask; //Bitmask. For categories see Constants.CollisionLayers 
    protected Body body;
    Shape bodyShape;
    
    public PhysicsEntity(){
    }
    
    public void setUpBody(Shape bodyShape, BodyDef.BodyType bodyType, Constants.CollisionLayer colCategory, short collisionMask) {
        this.bodyType = bodyType;
        this.bodyShape = bodyShape; 
        this.collisionCategory = colCategory;
        this.collisionMask = collisionMask;
    }
    
    public abstract void onContactBegin(PhysicsEntity other, Contact contact);
    public abstract void onContactEnd(PhysicsEntity other, Contact contact);
    
    @Override 
    public void loadEntity(){
        bd = new BodyDef();
        bd.type = bodyType;
        
        super.loadEntity();
    }
    
    @Override
    public void setSpritePositions(Vector2 pos){
        super.setSpritePositions(pos);
    }
    
    @Override
    public void addToWorld(GameWorld world){
        if(this.isLoaded()){
            body = world.physWorld.createBody(bd);
            body.setFixedRotation(true);
            Fixture f = body.createFixture(bodyShape, 1);
            Filter filter = f.getFilterData();
            filter.categoryBits = this.collisionCategory.getCode();
            filter.maskBits = this.collisionMask;
            f.setFilterData(filter);
            body.setTransform(new Vector2(this.getPosition()), 0);
            bodyShape.dispose();
        } else{
            System.out.println("Can't add unloaded entity to world!");
        }
        super.addToWorld(world);
    }
        
    @Override
    public void update(float deltatime){
        this.setSpritePositions(new Vector2(body.getPosition()).scl(Constants.PHYSICS_SCALE));
    }
    
    @Override
    public void setPosition(Vector2 pos){
        if(this.body != null){
            this.body.setTransform(new Vector2(pos).scl(1f/Constants.PHYSICS_SCALE), this.body.getAngle());
        }
    }
        
    @Override
    public Vector2 getPosition(){
        return new Vector2(this.body.getPosition()).scl(Constants.PHYSICS_SCALE);
    }
    
    @Override
    public void destroy(){
        if(!this.isDestroyed())
        {
            if(inWorld){
                world.physWorld.destroyBody(body);
            }
            else{
                System.out.println(this.name + "'s body wasn't in physWorld when tried to destroy.");
            }

            super.destroy();
        }
        
    }

    /**
     * @return the body
     */
    public Body getBody() {
        return body;
    }
    
}
