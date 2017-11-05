/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author Jens
 */
public class SpriteAnimationInfo {
    
    protected final String name;
    protected final int numFrames;
    protected final float fps;
    protected PlayMode mode = PlayMode.NORMAL;
    
    protected TextureRegion[] frames = null;
    
    protected int frameCols = -1;
    protected int frameRows = -1;
    protected int startCol = -1;
    protected int startRow = -1;
    protected int skipBegin = -1;
    
    public SpriteAnimationInfo(String name, int frameCols, int frameRows, float fps, int startCol, int startRow){
        this.name = name;
        this.frameCols = frameCols;
        this.frameRows = frameRows;
        this.fps = fps;
        this.startCol = startCol;
        this.startRow = startRow;
        
        this.numFrames = frameCols * frameRows;
        this.skipBegin = 0;
    }
    
    public SpriteAnimationInfo(String name, int frameCols, int frameRows, float fps, int startCol, int startRow, int numFrames, int skipBegin){
        this.name = name;
        this.frameCols = frameCols;
        this.frameRows = frameRows;
        this.fps = fps;
        this.startCol = startCol;
        this.startRow = startRow;
        
        this.numFrames = numFrames;
        this.skipBegin = skipBegin;
    }
    
    public SpriteAnimationInfo(String name, TextureRegion[] frames, float fps, PlayMode mode){
        this.name = name;
        this.frames = frames;
        this.numFrames = this.frames.length;
        this.fps = fps;
        this.mode = mode;
    }
    
}
