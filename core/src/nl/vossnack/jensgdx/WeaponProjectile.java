/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Shape;
import java.util.List;
import nl.vossnack.jensgdx.World.GameWorld;

/**
 *
 * @author Jens
 */
public abstract class WeaponProjectile extends PhysicsEntity{
    
    CharacterWeaponRanged weapon;
    
    public WeaponProjectile() {
        super();
    }
    
    public abstract void setUp();
    
    public abstract void addToWorld(GameWorld world, Vector2 location);
    
    public void CharacterWeaponProjectile(){
    
    }
    
    @Override
    public void onContactBegin(PhysicsEntity other, Contact contact){
        if(other != this.getWeapon().getOwner()){
            ((CharacterWeaponRanged)this.getWeapon()).onHit(contact, this, other);
        }
    }
    
    @Override
    public void onContactEnd(PhysicsEntity other, Contact contact){
        System.out.println("Contact end with: " + other);
    }
    
    public void setWeapon(CharacterWeaponRanged weapon){
        this.weapon = weapon;
    }
    
    public CharacterWeapon getWeapon(){
        return this.weapon;
    }
            
    
}
