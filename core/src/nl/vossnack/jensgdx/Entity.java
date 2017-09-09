/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx;

import nl.vossnack.jensgdx.World.GameWorld;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Jens
 */
public abstract class Entity {
    public String name = "";
    protected GameWorld world;
    
    private ArrayList<AnimatedSprite> sprites;
    protected Vector2 position;
    protected boolean inWorld;
    protected boolean loaded;
    protected boolean destroyed;
    
    public Entity(){
        sprites = new ArrayList<AnimatedSprite>();
    }
    
    public abstract void update(float deltatime);
    
    public void setUp(String imgPath, List<SpriteAnimationInfo> animations, int spritesheetPadding) {
        sprites.add(new AnimatedSprite(imgPath, animations, spritesheetPadding));
    }
    
    public void loadEntity(){
        sprites.get(0).loadAnimations();
        loaded = true;
    }
        
    public void addToWorld(GameWorld world){
        world.addEntity(this);
        this.world = world;
        this.inWorld = true;
    }
    
    public void destroy(){
        if(this.inWorld){
            this.world.removeEntity(this);
        }
        else{
            System.out.print(this.name + " was not in a world when destroy() was called");
        }
        
        destroyed = true;
    }
        
    /**
     * @return the loaded
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * @return the destroyed
     */
    public boolean isDestroyed() {
        return destroyed;
    }
    
    public void setPosition(Vector2 pos){
        this.position = pos;
    }
    
    public Vector2 getPosition(){
        return this.position;
    }
    
     
    public void setSpritePositions(Vector2 pos){
        for(Sprite s : this.sprites){
            s.setX(pos.x);
            s.setY(pos.y);
        }
        //this.sprites.get(index).setPosition(pos);
    }
    
    public void setSpritePosition(int index, Vector2 pos){
        Sprite s = this.sprites.get(index);
        s.setX(pos.x);
        s.setY(pos.y);
    }

    /**
     * @return the sprites
     */
    public ArrayList<AnimatedSprite> getSprites() {
        return sprites;
    }
    
    /**
     * @return the sprite at given index
     */
    public AnimatedSprite getSprite(int index) {
        return sprites.get(index);
    }
    

    /**
     * @param sprites the sprites to set
     */
    public void setSprites(ArrayList<AnimatedSprite> sprites) {
        this.sprites = sprites;
    }
    
    /**
     * @param sprite the sprite to add
     */
    public void addSprite(AnimatedSprite sprite) {
        this.sprites.add(sprite);
    }

    /**
     * @return the world
     */
    public GameWorld getWorld() {
        return world;
    }
}
