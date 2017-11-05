    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.dermetfan.gdx.physics.box2d.Box2DUtils;

/**
 *
 * @author Jens
 */
public abstract class CharacterWeaponMelee extends CharacterWeapon{
    
    PrimitiveEntity hitlocation;
    
    public CharacterWeaponMelee(CharacterWeaponStats stats, CharacterEntity owner) {
        super(stats);
        
        this.owner = owner;
        hitlocation = new PrimitiveEntity(PrimitiveEntity.PrimitiveType.Square, Color.CYAN, this.stats.range * Constants.PHYSICS_SCALE, this.stats.range * Constants.PHYSICS_SCALE);
        hitlocation.primitiveSprite.setPivot(new Vector2(-hitlocation.width/2f, -hitlocation.height/2f));
        hitlocation.addToWorld(this.owner.world);
    }
    
    @Override
    public boolean fire(int firemode){        
        if(this.canFire()){
            
            if(this.owner != null)
            {
                World world = this.owner.world.physWorld;
                Vector2 pos = this.owner.getPosition();
                Vector2 queryCenter = new Vector2(this.owner.getPosition()).add(this.owner.forward.cpy().scl(this.stats.range / 2f));
                hitlocation.setPosition(queryCenter.x * Constants.PHYSICS_SCALE, queryCenter.y * Constants.PHYSICS_SCALE);
                world.QueryAABB(new QueryCallback() {
                    @Override
                    public boolean reportFixture(Fixture fixture) {
                        CharacterWeaponMelee.this.hitQueryCallback(fixture);
                        return true; //Continue reporting fixtures
                    }
                }, (queryCenter.x) - (this.stats.range / 2f), (queryCenter.y) - (this.stats.range / 2f), (queryCenter.x )  + (this.stats.range / 2f), (queryCenter.y) + (this.stats.range / 2f));
            }
            
            return true;
        }
        
        return false;
    }
    
    public void hitQueryCallback(Fixture fixture){
        PhysicsEntity victim = this.owner.world.getPhysicsEntityByBody(fixture.getBody());
        
        if(victim != this.owner) 
            this.onHit(null, victim); //TODO: Remove contact from the onHit function defenition.
    }
    
    @Override
    public void update(float deltatime){
        
    }
    
    @Override
    public void onHit(Contact contact, PhysicsEntity victim){
        //System.err.println("onHit should be called with projectile parameter in ranged weapons");
    }
    
}
