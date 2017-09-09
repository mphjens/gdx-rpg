/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
public class AnimatedSprite extends Sprite{
    
    String spriteSheetFilename;
    Texture spriteSheet;
    int spriteSheetPadding;
    
    List<SpriteAnimationInfo> animationsInfo;
    HashMap<SpriteAnimationInfo, Animation<TextureRegion>> animations; // Must declare frame type (TextureRegion)
    
    private float animationtimer;
    
    private float animCompleteTime;
    private float lastCompleteCall;
    protected SpriteAnimationInfo baseAnimInfo;
    protected SpriteAnimationInfo currentanimationInfo;
    protected Animation<TextureRegion> currentanimation;
    protected boolean loop;
    protected boolean playing;
    
    protected ArrayList<AnimationEventListener> eventListeners;
    

    public AnimatedSprite(String spriteSheetFile, List<SpriteAnimationInfo> animations, int spriteSheetPadding){
        super();
        this.spriteSheetFilename = spriteSheetFile;
        this.animationsInfo = animations;
        this.animations = new HashMap<SpriteAnimationInfo, Animation<TextureRegion>>();
    }
    
    public void AddAnimationEventListener(AnimationEventListener listener){
        
    }
    
    public void dispatchEvent(){
        
    }
        
    public void loadAnimations(){
        // Load the sprite sheet as a Texture
        spriteSheet = new Texture(Gdx.files.internal(this.spriteSheetFilename));
        
        for(SpriteAnimationInfo ai : animationsInfo){
            if(animations.get(ai) != null){
                animations.remove(ai);
            }
           
            // Place the regions into a 1D array in the correct order, starting from the top 
            // left, going across first. The Animation constructor requires a 1D array.
            TextureRegion[] walkFrames = new TextureRegion[ai.numFrames];
            int index = 0;
            for (int i = (int)(ai.skipBegin / ai.frameCols); i < ai.frameRows; i++) {
                    for (int j = (ai.skipBegin % ai.frameCols); j < ai.frameCols; j++) {
                        walkFrames[index++] = new TextureRegion(this.spriteSheet, (int)((ai.startCol + j) * (this.getWidth() + spriteSheetPadding)), (int)((ai.startRow + i) * (this.getHeight() + spriteSheetPadding)), (int)(this.getWidth()), (int)(this.getHeight()) );     
                        if(index == ai.numFrames)
                            break;
                    }
                    
                if(index == ai.numFrames)
                    break;
            }

            // Initialize the Animation with the frame interval and array of frames
            this.animations.put(ai, new Animation<TextureRegion>( 1f/ai.fps, walkFrames));
        }
        
        this.setBaseAnimation(animationsInfo.get(0));
    }
    
    public void resumeAnimation(){
        this.playing = true;
    }
    
    public void pauseAnimation(){
        this.playing = false;
    }
    
    public void showFrame(int frameNum){
        this.animationtimer = frameNum * (1f/this.currentanimationInfo.fps);
    }
    
    @Override
    public void render(SpriteBatch batch, float deltatime){
        if(this.playing){
            animationtimer += deltatime;
            if(animationtimer - lastCompleteCall > animCompleteTime)
            {
                lastCompleteCall = animationtimer;
                if(currentanimationInfo != baseAnimInfo)
                    this.setCurrentanimation(baseAnimInfo);
            }
        }
        this.setTex(currentanimation.getKeyFrame(animationtimer, true));
        super.render(batch, deltatime);
    }

    /**
     * @param animInfo the info object of the animation to set, make sure it's present in the animations HashMap.
     */
    private void setCurrentanimation(SpriteAnimationInfo animInfo) {
        if(currentanimationInfo != animInfo){
            this.animationtimer = 0;
            this.currentanimationInfo = animInfo;
            this.currentanimation = this.animations.get(animInfo);
            this.animCompleteTime = animationtimer + (animInfo.numFrames / animInfo.fps);
            this.resumeAnimation();
        }
    }
    
    public void setBaseAnimation(SpriteAnimationInfo anim){
        baseAnimInfo = anim;
        setCurrentanimation(baseAnimInfo);
    }
    
    public void setBaseAnimation(String animname){
        setBaseAnimation(getAnimationByName(animname));
    }
    
    public SpriteAnimationInfo getAnimationByName(String animname){
        for(SpriteAnimationInfo i : this.animationsInfo){
            if(i.name == animname){
                return i;
            }
        }
        
        return null;
    }
    
    /**
     * @param animname name of the animation to play, make sure it's present in the animations HashMap
     */
    public void setCurrentanimation(String animname) {
        SpriteAnimationInfo anim = this.getAnimationByName(animname);
        if(anim != null)
            this.setCurrentanimation(anim);
        else
            System.out.println(animname + " not found on animated sprite");
    }
    
    public void playOverBaseAnimation(String animname){
        SpriteAnimationInfo anim = this.getAnimationByName(animname);
        if(anim != null){
            this.setCurrentanimation(anim);
        }
        
    }
    
}
