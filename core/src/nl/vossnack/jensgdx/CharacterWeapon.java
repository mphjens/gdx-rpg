/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx;

import com.badlogic.gdx.physics.box2d.Contact;



/**
 *
 * @author Jens
 */
public abstract class CharacterWeapon {
    
    protected boolean equipped;
    protected boolean firing;
    protected CharacterEntity owner;
    protected CharacterWeaponStats stats;
    
    public CharacterWeapon(){
        
    }
        
    public abstract void onHit(Contact contact, PhysicsEntity victim);
        
    public abstract boolean canFire();
    
    public abstract boolean fire(int firemode);
    
    public abstract void update(float deltatime);
    
    public void setStats(CharacterWeaponStats stats){
        this.stats = stats;
    }

    public void onEquip(CharacterEntity owner){
        this.owner = owner;
        this.equipped = true;
    }
    
    public void onDeEquip(){
        this.owner = null;
        this.equipped = false;
    }
    
    /**
     * @return the equipped
     */
    public boolean isEquipped() {
        return equipped;
    }

    /**
     * @return the owner
     */
    public CharacterEntity getOwner() {
        return owner;
    }

    /**
     * @return the stats
     */
    public CharacterWeaponStats getStats() {
        return stats;
    }
    
    
    
    
}
