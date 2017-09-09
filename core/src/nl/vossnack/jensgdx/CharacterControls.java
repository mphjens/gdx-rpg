/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import static java.lang.System.out;

/**
 *
 * @author Jens
 */
public class CharacterControls implements InputProcessor{
    boolean MOVE_UP,
            MOVE_DOWN,
            MOVE_LEFT,
            MOVE_RIGHT,
            ACTION,
            ATTACK,
            BLOCK,
            SHOW_DEBUG;
    
    Vector2 AimDirection;

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode){
            case Input.Keys.A:
                this.MOVE_LEFT = true;
                break;
            case Input.Keys.D:
                this.MOVE_RIGHT = true;
                break;
            case Input.Keys.W:
                this.MOVE_UP = true;
                break;
            case Input.Keys.S:
                this.MOVE_DOWN = true;
                break;
            case Input.Keys.SPACE:
                this.ATTACK = true;
                break;
            case Input.Keys.NUM_1:
                this.SHOW_DEBUG = true;
                break;
        }
        
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        
        switch(keycode){
            case Input.Keys.A:
                this.MOVE_LEFT = false;
                break;
            case Input.Keys.D:
                this.MOVE_RIGHT = false;
                break;
            case Input.Keys.W:
                this.MOVE_UP = false;
                break;
            case Input.Keys.S:
                this.MOVE_DOWN = false;
                break;
            case Input.Keys.SPACE:
                this.ATTACK = false;
                break;
            case Input.Keys.NUM_1:
                this.SHOW_DEBUG = false;
                break;
        }
        
        return false;
    }

  @Override
    public boolean keyTyped(char character) {

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                 
        if(screenX + 200 > (Gdx.graphics.getWidth() / 2f) && screenX - 200 < (Gdx.graphics.getWidth() / 2f)){
            if(screenY + 200 > (Gdx.graphics.getHeight()/ 2f) && screenY - 200 < (Gdx.graphics.getHeight()/ 2f)){
             this.keyDown(Input.Keys.SPACE);
            }
        }
         return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
     
            this.keyUp(Input.Keys.D);

            this.keyUp(Input.Keys.A);

            this.keyUp(Input.Keys.W);

            this.keyUp(Input.Keys.S);
            
            this.keyUp(Input.Keys.SPACE);
        
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(screenX - 200 > Gdx.graphics.getWidth() / 2f){
            this.keyDown(Input.Keys.D);
            this.keyUp(Input.Keys.A);
        }
        else if(screenX + 200 < Gdx.graphics.getWidth() / 2f){
            this.keyUp(Input.Keys.D);
            this.keyDown(Input.Keys.A);
        }
        
        if(screenY + 100 < Gdx.graphics.getHeight()/ 2f){
            this.keyDown(Input.Keys.W);
            this.keyUp(Input.Keys.S);
        }
        else if(screenY - 100 > Gdx.graphics.getHeight()/ 2f){
            this.keyUp(Input.Keys.W);
            this.keyDown(Input.Keys.S);
        }

        
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
    
    

}
