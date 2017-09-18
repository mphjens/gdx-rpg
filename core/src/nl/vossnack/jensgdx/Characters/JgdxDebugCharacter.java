/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx.Characters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import java.util.ArrayList;
import nl.vossnack.jensgdx.AnimatedSprite;
import nl.vossnack.jensgdx.CharacterEntity;
import nl.vossnack.jensgdx.CharacterStats;
import nl.vossnack.jensgdx.Constants;
import nl.vossnack.jensgdx.World.GameWorld;
import nl.vossnack.jensgdx.SpriteAnimationInfo;

/**
 *
 * @author Jens
 */
public class JgdxDebugCharacter extends CharacterEntity{
    public JgdxDebugCharacter(){      
        
           
        CircleShape colShape = new CircleShape();
        //TODO: these units are box2d units (the world is scaled so 1 unit = 1 tile = 16px), support pixel space as well. 
        colShape.setRadius(0.5f);
        colShape.setPosition(new Vector2(0f, 0.5f));
        
        super.setUpBody(colShape, BodyDef.BodyType.DynamicBody, 
                Constants.CollisionLayer.CATEGORY_FRIENDLY, 
                Constants.CollisionLayer.createMask(Constants.CollisionLayer.CATEGORY_ENEMY.getCode(), 
                                                        Constants.CollisionLayer.CATEGORY_BOUNDS.getCode(), 
                                                        Constants.CollisionLayer.CATEGORY_OBSTACLE.getCode()));
        
        
        ArrayList<SpriteAnimationInfo> anims = new ArrayList<SpriteAnimationInfo>();
        anims.add(new SpriteAnimationInfo("walk-up", 9, 1, 14f, 0, 0));
        anims.add(new SpriteAnimationInfo("walk-left", 9, 1, 14f, 0, 1));
        anims.add(new SpriteAnimationInfo("walk-down", 9, 1, 14f, 0, 2));
        anims.add(new SpriteAnimationInfo("walk-right", 9, 1, 14f, 0, 3));
        
        AnimatedSprite bodyspr = new AnimatedSprite("rpg_sprite_walk.png", anims, 0);
        bodyspr.setSize(new Vector2(24, 32));
        bodyspr.setPivot(new Vector2(12, 0));
        bodyspr.setScale(new Vector2(0.75f, 0.75f));
        bodySprite = bodyspr;
        this.addSprite(bodySprite);
    }
}
