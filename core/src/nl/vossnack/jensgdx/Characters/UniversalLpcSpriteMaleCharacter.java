/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx.Characters;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;
import nl.vossnack.jensgdx.AnimatedSprite;
import nl.vossnack.jensgdx.CharacterEntity;
import nl.vossnack.jensgdx.CharacterStats;
import nl.vossnack.jensgdx.Constants;
import nl.vossnack.jensgdx.Constants.CollisionLayer;
import nl.vossnack.jensgdx.World.GameWorld;
import nl.vossnack.jensgdx.SpriteAnimationInfo;

/**
 *
 * @author Jens
 */
public class UniversalLpcSpriteMaleCharacter extends CharacterEntity{
    PointLight torchLight;
    
    
    public UniversalLpcSpriteMaleCharacter(){
        
        
        ArrayList<SpriteAnimationInfo> anims = new ArrayList<SpriteAnimationInfo>();
        anims.add(new SpriteAnimationInfo("walk-up", 9, 1, 14f, 0, 8));
        anims.add(new SpriteAnimationInfo("walk-left", 9, 1, 14f, 0, 9));
        anims.add(new SpriteAnimationInfo("walk-down", 9, 1, 14f, 0, 10));
        anims.add(new SpriteAnimationInfo("walk-right", 9, 1, 14f, 0, 11));
        
        anims.add(new SpriteAnimationInfo("attack-up", 6, 1, 14f, 0, 12));
        anims.add(new SpriteAnimationInfo("attack-left", 6, 1, 14f, 0, 13));
        anims.add(new SpriteAnimationInfo("attack-down", 6, 1, 14f, 0, 14));
        anims.add(new SpriteAnimationInfo("attack-right", 6, 1, 14f, 0, 15));
        
        CircleShape colShape = new CircleShape();
        //TODO: these units are box2d units (the world is scaled so 1 unit = 1 tile = 16px), support pixel space as well. 
        colShape.setRadius(0.35f);
        colShape.setPosition(new Vector2(0f, 0.35f));
        
        super.setUpBody(colShape, BodyDef.BodyType.DynamicBody, 
                Constants.CollisionLayer.CATEGORY_FRIENDLY, 
                Constants.CollisionLayer.createMask(Constants.CollisionLayer.CATEGORY_ENEMY.getCode(), 
                                                        Constants.CollisionLayer.CATEGORY_BOUNDS.getCode(), 
                                                        Constants.CollisionLayer.CATEGORY_OBSTACLE.getCode()));
        
        CharacterStats charStats = new CharacterStats();
        charStats.speed = 0.7f;

        AnimatedSprite bodyspr = new AnimatedSprite("universal-lpc-sprite_male_01_full.png" , anims, 0);
        bodyspr.setSize(new Vector2(64, 64));
        bodyspr.setScale(new Vector2(0.35f, 0.35f));
        bodyspr.setPivot(new Vector2(32 * 0.35f, 0));
        this.bodySprite = bodyspr;
        this.addSprite(bodySprite);
        
        
        
      
    }
    
    @Override
    public void addToWorld(GameWorld world){
        super.addToWorld(world);
//        torchLight = new PointLight(world.lightingRayHandler, Constants.LIGHT_RAY_COUNT, Color.valueOf("#131313"), 2.5f, this.body.getPosition().x, this.body.getPosition().y);
//        torchLight.setXray(true);
//        torchLight.attachToBody(body);
//        torchLight.setIgnoreAttachedBody(true);
//        Filter f = new Filter();
//        f.categoryBits = CollisionLayer.CATEGORY_FRIENDLY.getCode();
//        f.maskBits = CollisionLayer.createMask(CollisionLayer.CATEGORY_ENEMY.getCode() , CollisionLayer.CATEGORY_OBSTACLE.getCode());
//        torchLight.setContactFilter(f);
       
        
    }
    
    @Override
    public void destroy(){
        if(torchLight != null)
            torchLight.remove(true);
        super.destroy();
    }
    
    
    @Override
    public void update(float deltatime){
        super.update(deltatime);

        
    }
    

}
