/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx;

/**
 *
 * @author Jens
 */
public class SpriteAnimationInfo {
    
    protected final String name;
    protected final int numFrames;
    protected final float fps;
    
    protected final int frameCols;
    protected final int frameRows;
    protected final int startCol;
    protected final int startRow;
    protected final int skipBegin;
    
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
    
}
