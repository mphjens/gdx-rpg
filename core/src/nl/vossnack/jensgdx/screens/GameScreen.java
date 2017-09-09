/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import nl.vossnack.jensgdx.Entity;
import nl.vossnack.jensgdx.GameController;
import nl.vossnack.jensgdx.UI.GameUI;
import nl.vossnack.jensgdx.World.GameWorld;

/**
 *
 * @author Jens
 */
public class GameScreen implements Screen{
    
    public GameController controller;
    public GameWorld world;
    public GameUI gameUI;
    public Vector2 screenSize;
    
    SpriteBatch UISpriteBatch;

    @Override
    public void show() {
        Gdx.graphics.setVSync(true);
        screenSize = new Vector2();
        world = new GameWorld();
        controller = new GameController(this);
        gameUI = new GameUI(world, controller);
        UISpriteBatch = new SpriteBatch();
        
    }
    
    

    @Override
    public void render(float delta) {
        controller.update(delta);
        
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        world.render(delta);
        UISpriteBatch.begin();
        //Do UI rendering here..
        gameUI.render(UISpriteBatch, delta);
        UISpriteBatch.end();
        
    }

    @Override
    public void resize(int width, int height) {
        screenSize.set(width, height);
        UISpriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        if(world != null){
            world.onResize(width, height);
        }
    }

    @Override
    public void pause() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resume() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void hide() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dispose() {
        world.dispose();
    }
    
}
