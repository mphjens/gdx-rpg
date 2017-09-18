/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

/**
 *
 * @author Jens
 */
public class Sprite implements Disposable{
    
    com.badlogic.gdx.graphics.g2d.Sprite gdxSprite;
    
    protected Vector2 pivot;
    
    public Sprite(){

        pivot = new Vector2();
        gdxSprite = new com.badlogic.gdx.graphics.g2d.Sprite();
    }
    
    public Sprite(TextureRegion tex, float width, float height)
    {
        pivot = new Vector2();
        gdxSprite = new com.badlogic.gdx.graphics.g2d.Sprite(tex);
        this.setSize(width, height);
    }
    
    public void render(SpriteBatch batch, float deltime){
        this.gdxSprite.draw(batch);
        //batch.draw(getTex(), (pos.x + getPosition().x - getPivot().x), (pos.y + getPosition().y - getPivot().y), getPivot().x, getPivot().y, getSize().x, getSize().y, getScale().x, getScale().y, getRotation());
    }

    /**
     * @return the pivot
     */
    public Vector2 getOrigin() {
        return new Vector2(this.gdxSprite.getOriginX(), this.gdxSprite.getOriginY());
    }

    /**
     * @param pivot the pivot to set
     */
    //public void setOrigin(Vector2 pivot) {
   //     this.gdxSprite.setOrigin(pivot.x * this.getScaleX(), pivot.y * this.getScaleY());
    //}
    
    public void setPivot(Vector2 pivot){
        this.pivot = pivot;
    }

    /**
     * @return the position
     */
    public float getX() {
        return this.gdxSprite.getX() + pivot.x;
    }
    
    public float getY() {
        return this.gdxSprite.getY() + pivot.y;
    }

    public void setX(float x) {
        this.gdxSprite.setX(x - pivot.x);
    }
    

    public void setY(float y) {
        this.gdxSprite.setY(y - pivot.y);
    }
    
    public void setSize(Vector2 size)
    {
        this.gdxSprite.setSize(size.x, size.y);
    }
    
    public void setSize(float width, float height)
    {
        this.gdxSprite.setSize(width, height);
    }

    public float getWidth() {
        return this.gdxSprite.getWidth();
    }
    
    public float getHeight() {
        return this.gdxSprite.getHeight();
    }

    public float getScaleX() {
        return this.gdxSprite.getScaleX();
    }
    
    public float getScaleY() {
        return this.gdxSprite.getScaleY();
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(Vector2 scale) {
        this.gdxSprite.setScale(scale.x, scale.y);
    }

    /**
     * @return the rotation
     */
    public float getRotation() {
        return this.gdxSprite.getRotation();
    }

    /**
     * @param rotation the rotation to set
     */
    public void setRotation(float rotation) {
        this.gdxSprite.setRotation(rotation);
    }

    /**
     * @return the tex
     */
    public com.badlogic.gdx.graphics.g2d.Sprite getGdxSprite() {
        return gdxSprite;
    }

    /**
     * @param tex the tex to set
     */
    public void setTex(TextureRegion tex) {
        gdxSprite.setRegion(tex);
        //this.tex = tex;
    }

    @Override
    public void dispose() {
        
    }
    
}
