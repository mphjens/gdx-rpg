/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author Jens
 */
public class UIConstants {
    public static final BitmapFont UIFont = new BitmapFont(Gdx.files.internal("fonts/cooper_16.fnt"));
    
    public static Vector2 WorldToScreenCoords(Vector2 worldCoords, OrthographicCamera cam){
        Vector3 retval3 = cam.project(new Vector3(worldCoords.x, worldCoords.y, 0));
        return new Vector2(retval3.x,retval3.y);
    }
    
    public static Vector2 ScreenToWorldCoords(Vector2 screenCoords, OrthographicCamera cam){
        Vector3 retval3 = cam.unproject(new Vector3(screenCoords.x, screenCoords.y, 0));
        return new Vector2(retval3.x,retval3.y);
    }
}
