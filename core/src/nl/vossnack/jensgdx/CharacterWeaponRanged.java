/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jens
 */
public abstract class CharacterWeaponRanged extends CharacterWeapon{
    
    Class ProjectilePrototype;
    
    protected Vector2 projectileOffset;
    protected List<WeaponProjectile> projectiles;
    
    public CharacterWeaponRanged() {
        super();
        this.projectiles = new ArrayList<WeaponProjectile>();
    }
    
    public void setProjectilePrototype(Class prototype){
        this.ProjectilePrototype = prototype;
    }
    
    public abstract void onHit(Contact contact, WeaponProjectile p, PhysicsEntity victim);
    
    @Override
    public void onHit(Contact contact, PhysicsEntity victim){
        System.err.println("onHit should be called with projectile parameter in ranged weapons");
    }
    
    @Override
    public void update(float deltatime){
        for(int i = 0; i < getProjectiles().size(); i++){
            WeaponProjectile p = getProjectiles().get(i);
            
            if(p.isDestroyed()){
                getProjectiles().remove(i);
                i--;
            }
        }
    }
    
    @Override
    public boolean fire(int firemode){
        if(this.canFire()){
            WeaponProjectile p = null;
            
            try {
                p = (WeaponProjectile)ProjectilePrototype.newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(CharacterWeaponRanged.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(CharacterWeaponRanged.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(p != null){
                p.setWeapon(this);
                p.setUp();
                p.loadEntity();
                p.addToWorld(this.owner.world, this.owner.getPosition().add(projectileOffset));
                //p.setSpritePositions(this.getOwner().getSpritePosition(0));
                p.getBody().setSleepingAllowed(false);
                p.getBody().isBullet();
                p.getBody().getFixtureList().get(0).setSensor(true);
                getProjectiles().add(p);
            }
            
            
            return true;
        }
        
        return false;
    }

    /**
     * @return the projectiles
     */
    public List<WeaponProjectile> getProjectiles() {
        return projectiles;
    }

    /**
     * @return the projectileOffset
     */
    public Vector2 getProjectileOffset() {
        return projectileOffset;
    }

    /**
     * @param projectileOffset the projectileOffset to set
     */
    public void setProjectileOffset(Vector2 projectileOffset) {
        this.projectileOffset = projectileOffset;
    }
    
    
}
