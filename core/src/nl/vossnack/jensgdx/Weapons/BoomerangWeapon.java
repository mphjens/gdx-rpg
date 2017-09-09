/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx.Weapons;

import Projectiles.BoomerangProjectile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import nl.vossnack.jensgdx.CharacterEntity;
import nl.vossnack.jensgdx.CharacterWeaponRanged;
import nl.vossnack.jensgdx.CharacterWeaponStats;
import nl.vossnack.jensgdx.PhysicsEntity;
import nl.vossnack.jensgdx.UI.UIConstants;
import nl.vossnack.jensgdx.WeaponProjectile;

/**
 *
 * @author Jens
 */
public class BoomerangWeapon extends CharacterWeaponRanged{
    
    boolean onWayBack;
    
    Vector2 firePosition;
    Vector2 turnPosition;
    float timeSinceFire = Float.MAX_VALUE;
    float timeSinceReturn = Float.MAX_VALUE;
    Vector2 velVector;
    Vector2 targetDistance;
    
    //TODO: MOVE THESE TO CharacterWeaponStats !!!
    float cooldown = 0.2f;
    float boomerangRange = 70f;
    float boomerangSpeed = 6f;
    
    //Non scalable parameters
    float percision = 4f;
    float blockMovementTime = 0.2f;
    
    public BoomerangWeapon() {
        super();
        this.setProjectilePrototype(BoomerangProjectile.class);
        this.setStats(new CharacterWeaponStats());
        this.setProjectileOffset(new Vector2(0f, 8f));
        targetDistance = new Vector2();
    }
    
    @Override
    public void update(float deltatime){
        super.update(deltatime);
        if(!this.getProjectiles().isEmpty()){
            BoomerangProjectile p = (BoomerangProjectile)this.getProjectiles().get(0);
            
            velVector = new Vector2();
            if(!onWayBack){
                targetDistance.set(velVector.add(turnPosition).sub(p.getPosition()));
               
                if(targetDistance.len() < percision){
                    onWayBack = true;
                }
            }   
            else{
                targetDistance.set(velVector.add(this.getOwner().getPosition()).sub(p.getPosition()));
                
                if(targetDistance.len() < percision){
                    timeSinceReturn = 0;
                    p.destroy();
                    onWayBack = false;
                    return;
                }
            }
            
            velVector = new Vector2(targetDistance).nor().scl(boomerangSpeed);
            p.getBody().setLinearVelocity(velVector); 
        }
        
        this.getOwner().setCanMove(timeSinceFire > blockMovementTime);
        timeSinceFire += deltatime;
        timeSinceReturn += deltatime;
    }
    
    @Override
    public void onHit(Contact contact, WeaponProjectile p, PhysicsEntity victim) {
        this.onWayBack = true;
        if(victim != null){
            if(victim instanceof CharacterEntity){
                
            }
        }
    }
    
    @Override
    public boolean canFire(){
        return this.getProjectiles().isEmpty() && timeSinceReturn >= cooldown;
    }
    
    @Override
    public boolean fire(int firemode){
        if(super.fire(firemode)){
            timeSinceFire = 0;
            System.out.println("Fired boomerang at " + this.getOwner().getPosition().toString());
            this.firePosition = this.getOwner().getPosition().add(this.projectileOffset);
                        
            //this.turnPosition = UIConstants.ScreenToWorldCoords(new Vector2(Gdx.input.getX(), (float)Gdx.input.getY()), this.owner.getWorld().camera);
            switch(this.getOwner().getFacingDirection()){
                case NORTH:
                    this.turnPosition = new Vector2(this.firePosition).add(0,boomerangRange);
                    break;
                case EAST:
                    this.turnPosition = new Vector2(this.firePosition).add(boomerangRange, 0);
                    break;
                case SOUTH:
                    this.turnPosition = new Vector2(this.firePosition).add(0,-boomerangRange);
                    break;
                case WEST:
                    this.turnPosition = new Vector2(this.firePosition).add(-boomerangRange, 0);
                    break;
            }
            
            return true;
        }
        
        return false;
    }
    
}
