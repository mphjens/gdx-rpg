/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Shape;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jens
 */
public class WorldEntity extends PhysicsEntity{
    public WorldEntity(Cell cell, Shape shape, int x, int y){
        if(cell.getTile() instanceof AnimatedTiledMapTile){
            AnimatedTiledMapTile tile = (AnimatedTiledMapTile)cell.getTile();
            float fps = 1f/(tile.getAnimationIntervals()[0] / 1000f);
            TextureRegion[] animFrames = new TextureRegion[tile.getFrameTiles().length];
            for(int i = 0; i < animFrames.length; i++){
                animFrames[i] = tile.getFrameTiles()[i].getTextureRegion();
            }
            
            SpriteAnimationInfo anim = new SpriteAnimationInfo("world-idle", animFrames, fps, PlayMode.LOOP);
            List<SpriteAnimationInfo> anims = new ArrayList<SpriteAnimationInfo>();
            anims.add(anim);
            System.out.println("Cell size: " + (cell.getTile().getTextureRegion().getRegionWidth()) + ", " + cell.getTile().getTextureRegion().getRegionHeight());
            this.setUp(anims, cell.getTile().getTextureRegion().getRegionWidth(), cell.getTile().getTextureRegion().getRegionHeight());
            ((AnimatedSprite)this.getSprite(0)).loadAnimations();
        }else{
            this.setUp(cell.getTile().getTextureRegion(), cell.getTile().getTextureRegion().getRegionWidth(), cell.getTile().getTextureRegion().getRegionHeight());
        }
        
        this.getSprite(0).setPivot(new Vector2(0,0));
        
        
        this.setUpBody(shape, bodyType.StaticBody, Constants.CollisionLayer.CATEGORY_OBSTACLE, 
        Constants.CollisionLayer.createMask(
                Constants.CollisionLayer.CATEGORY_FRIENDLY.getCode(),
                Constants.CollisionLayer.CATEGORY_ENEMY.getCode())
        );
        
        this.loadEntity();
    }

    @Override
    public void onContactBegin(PhysicsEntity other, Contact contact) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onContactEnd(PhysicsEntity other, Contact contact) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
