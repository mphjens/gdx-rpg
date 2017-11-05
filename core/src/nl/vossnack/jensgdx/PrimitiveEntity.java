/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Jens
 */
public class PrimitiveEntity extends Entity{
    public enum PrimitiveType{
        Square,
        Triangle,
        Circle
    }
    
    PrimitiveType type;
    Color color;
    float width, height;
    boolean isDirty;
    
    Sprite primitiveSprite;
    
    public PrimitiveEntity(PrimitiveType type, Color color, float width, float height){
        this.type = type;
        this.width = width;
        this.height = height;
        this.color = color;
        
        reDrawTexture();
        this.loadEntity();
    }
    
    private void reDrawTexture(){
        Pixmap myPixMap = new Pixmap((int)width, (int)height, Pixmap.Format.RGBA4444);
        myPixMap.setColor(color);
        
        switch(type){
            case Square:
                myPixMap.fillRectangle(0, 0, (int)width, (int)height);
                break;
            case Triangle:
                myPixMap.fillTriangle(0, 0, (int)width, 0, (int)width /2, (int)height);
                break;
            case Circle:
                myPixMap.fillCircle((int)Math.min(width, height) / 2, (int)Math.min(width, height) / 2, (int)Math.min(width, height) / 2);
                break;
        }
        
        TextureRegion tex = new TextureRegion(new Texture(myPixMap));
        myPixMap.dispose();
        
        if(primitiveSprite == null)
        {
            primitiveSprite = new Sprite(tex, width, height);
            this.addSprite(primitiveSprite);
        }
        else{
            this.getSprite(0).setTex(tex);
        }
        
        
        
        
    }
    
    public void setColor(Color color){
        isDirty = true;
        this.color = color;
    }
    
    public void setSize(float width, float height){
        isDirty = true;
        this.width = width;
        this.height = height;
    }
    
    public void setType(PrimitiveType type){
        isDirty = true;
        this.type = type;
    }
    
    @Override
    public void update(float deltatime) {
        if(isDirty){
            reDrawTexture();
            this.isDirty = false;
        }
        super.update(deltatime);
        
        //this.getSprite(0).setRotation(this.getSprite(0).getRotation() + 5f);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
    
}
