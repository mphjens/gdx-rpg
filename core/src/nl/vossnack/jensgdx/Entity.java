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
import com.badlogic.gdx.utils.Disposable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Jens
 */
public abstract class Entity implements Disposable{
    public String name = "";
    protected GameWorld world;
    
    private ArrayList<Sprite> sprites;
    protected Vector2 location;
    protected boolean inWorld;
    protected boolean loaded;
    protected boolean destroyed;
    
    public Entity(){
        sprites = new ArrayList<Sprite>();
        location = new Vector2();
    }
    
    public void update(float deltatime){
        this.setSpritePositions(this.location);
    }
            
    public void setUp(TextureRegion tex, float width, float height) {
        sprites.add(new Sprite(tex, width, height));
    }
    
    public void setUp(List<SpriteAnimationInfo> animations, float width, float height){
        sprites.add(new AnimatedSprite(animations, width, height));
    }
        
    public void loadEntity(){
        loaded = true;
    }
        
    public void addToWorld(GameWorld world){
        world.addEntity(this);
        this.world = world;
        this.inWorld = true;
    }
    
    public void destroy(){
        //if(this.inWorld){
        //    this.world.removeEntity(this);
        //}
        //else{
        //    System.out.print(this.name + " was not in a world when destroy() was called");
        //}
        
        destroyed = true;
        this.dispose();
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
        this.location = pos;
    }

    public void setPosition(float x, float y){
        this.setPosition(new Vector2(x, y));
    }
    
    public Vector2 getLocation(){
        return this.location;
    }
    
     
    public void setSpritePositions(Vector2 pos){
        for(Sprite s : this.sprites){
            if(s.anchored){
                s.setX(pos.x);
                s.setY(pos.y);
            }
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
    public ArrayList<Sprite> getSprites() {
        return sprites;
    }
    
    /**
     * @return the sprite at given index
     */
    public Sprite getSprite(int index) {
        return sprites.get(index);
    }
    

    /**
     * @param sprites the sprites to set
     */
    public void setSprites(ArrayList<Sprite> sprites) {
        this.sprites = sprites;
    }
    
    /**
     * @param sprite the sprite to add
     */
    public void addSprite(Sprite sprite) {
        this.sprites.add(sprite);
    }

    /**
     * @param sprite the sprite to remove
     */
    public void removeSprite(Sprite sprite) {
        this.sprites.remove(sprite);
        sprite.dispose();
    }
    
    public void removeAllSprites() {
        while(sprites.size() > 0){
            sprites.get(0).dispose();
            sprites.remove(0);
        }
    }
    
    /**
     * @return the world
     */
    public GameWorld getWorld() {
        return world;
    }

    @Override
    public void dispose() {
        this.removeAllSprites();
    }
}
