/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx.UI;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import nl.vossnack.jensgdx.GameController;
import nl.vossnack.jensgdx.World.GameWorld;

/**
 *
 * @author Jens
 */
public class GameUI {
    
    public GameController controller;
    public GameWorld world;
    
    double timer = 0;
    
    public GameUI(GameWorld world, GameController controller){
        this.world = world;
        this.controller = controller;
    }
    
    public void render(SpriteBatch batch, float deltatime){
        timer += deltatime;
        
        UIConstants.UIFont.draw(batch, (1/deltatime) + " FPS", 16, 16);
        
        Vector2 screenLoc = UIConstants.WorldToScreenCoords(this.controller.controlledCharacter.getLocation(), this.world.camera);
        UIConstants.UIFont.draw(batch, "", screenLoc.x, screenLoc.y);
    }
    
}
