/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx.Weapons;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import java.util.ArrayList;
import java.util.List;
import nl.vossnack.jensgdx.AnimatedSprite;
import nl.vossnack.jensgdx.CharacterEntity;
import nl.vossnack.jensgdx.CharacterWeaponMelee;
import nl.vossnack.jensgdx.CharacterWeaponStats;
import nl.vossnack.jensgdx.Constants;
import nl.vossnack.jensgdx.PhysicsEntity;
import nl.vossnack.jensgdx.SpriteAnimationInfo;

/**
 *
 * @author Jens
 */
public class DebugSwordWeapon extends CharacterWeaponMelee{
    
    float timeSinceFire = 0;
    
    //AnimatedSprite swingEffect;
    

    public DebugSwordWeapon(CharacterWeaponStats stats, CharacterEntity owner) {
        super(stats, owner);
        
        List<SpriteAnimationInfo> anims = new ArrayList<SpriteAnimationInfo>();
        SpriteAnimationInfo anim = new SpriteAnimationInfo("main", 8, 8, 120f, 0, 0);
        anims.add(anim);
        
//        this.swingEffect = new AnimatedSprite("boom3.png", anims, 128, 128);
//        this.swingEffect.anchored = false;
//        this.swingEffect.setScale(new Vector2(0.25f, 0.25f));
//        this.swingEffect.setPivot(new Vector2(-16f, -16f));
//        this.swingEffect.loadAnimations();
        
        this.stats.speed = 3f;
        
    }
    
    @Override
    public void onHit(Contact contact, PhysicsEntity victim)
    {
        System.out.println("Debug sword hit: " + victim);
        if(victim != null){
            victim.getBody().applyLinearImpulse(new Vector2(this.owner.getPosition()).mulAdd(victim.getLocation(), -1f/Constants.PHYSICS_SCALE).nor().scl(-this.stats.damage), this.owner.getPosition(), true);
        }
        
        super.onHit(contact, victim);
    }
    
    @Override
    public void update(float deltatime){
        super.update(deltatime);
        Vector2 pos = this.owner.getPosition();
//        swingEffect.setX(pos.x);
//        swingEffect.setY(pos.y);
        
        if(timeSinceFire > 1f/this.stats.speed){
            //this.owner.removeSprite(swingEffect);
            this.owner.setCanMove(true);
        }
            
        
        timeSinceFire += deltatime;
    }
    
    @Override
    public boolean fire(int firemode){
        if(super.fire(firemode))
        {
            this.timeSinceFire = 0;
            //this.owner.addSprite(swingEffect);
            this.owner.setCanMove(false);  
            this.owner.bodySprite.playOverBaseAnimation("attack-" + this.owner.getFacingDirection().name());
            
            //this.swingEffect.playOverBaseAnimation("main");
            //swingEffect.setLocation(new Vector2(this.owner.getLocation()).mulAdd(this.owner.getForward(), 16f));
            return true;
        }
        
        return false;
    }

    @Override
    public boolean canFire() {
        return timeSinceFire > 1f/this.stats.speed;
    }
    
}
