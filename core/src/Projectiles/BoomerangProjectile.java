/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Shape;
import java.util.ArrayList;
import java.util.List;
import nl.vossnack.jensgdx.Constants;
import nl.vossnack.jensgdx.World.GameWorld;
import nl.vossnack.jensgdx.PhysicsEntity;
import nl.vossnack.jensgdx.SpriteAnimationInfo;
import nl.vossnack.jensgdx.WeaponProjectile;

/**
 *
 * @author Jens
 */
public class BoomerangProjectile extends WeaponProjectile{
    
    public BoomerangProjectile() {
        super();
    }
    
    
    @Override
    public void setUp() {
        ArrayList<SpriteAnimationInfo> anims = new ArrayList<SpriteAnimationInfo>();
        anims.add(new SpriteAnimationInfo("inair", 8, 1, 12f, 0, 0));
        
        CircleShape colShape = new CircleShape();
        colShape.setRadius(0.17f);
        //colShape.setPosition(new Vector2(0.5f - (0.25f/2f), 0.5f - (0.25f/2f)));
        
        super.setUp(colShape, BodyType.DynamicBody,
                    Constants.CollisionLayer.CATEGORY_FRIENDLY, 
                    Constants.CollisionLayer.createMask(Constants.CollisionLayer.CATEGORY_ENEMY.getCode(),
                                                        Constants.CollisionLayer.CATEGORY_OBSTACLE.getCode()),
                    "boomerang48px_8.png", anims, 0);
        
        this.getSprite(0).setSize(new Vector2(48, 48));
        this.getSprite(0).setScale(new Vector2(0.17f, 0.17f));
        this.getSprite(0).setPivot(new Vector2(24 * 0.17f, 24 * 0.17f));
        
        
    }
    
    @Override
    public void addToWorld(GameWorld world, Vector2 location){
        super.addToWorld(world);
        this.setPosition(location);
    }
    
    @Override
    public void update(float deltatime) {
        super.update(deltatime);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
