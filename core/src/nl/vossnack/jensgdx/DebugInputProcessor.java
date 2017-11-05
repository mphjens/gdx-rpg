/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import java.util.List;
import nl.vossnack.jensgdx.UI.UIConstants;
import nl.vossnack.jensgdx.World.AStarPathfindingAlgorithm;
import nl.vossnack.jensgdx.World.GameWorld;
import nl.vossnack.jensgdx.World.PathFinderNode;

/**
 *
 * @author Jens
 */
public class DebugInputProcessor implements InputProcessor{
    GameController gameController;
    
    AStarPathfindingAlgorithm algo;
    
    public DebugInputProcessor(GameController gameController){
        this.gameController = gameController;
        algo = new AStarPathfindingAlgorithm(gameController._world.pathFinder);
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.R){
            this.gameController._world.loadMap("maps/atlasmap_tilecollision_test.tmx");
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            Vector2 WorldLoc = UIConstants.ScreenToWorldCoords(new Vector2(screenX, screenY), gameController._world.camera);
            int x = (int)(WorldLoc.x / Constants.TILE_SIZE);
            int y = (int)(WorldLoc.y / Constants.TILE_SIZE);
            int playerX = (int)(this.gameController.controlledCharacter.getLocation().x / Constants.TILE_SIZE);
            int playerY = (int)((this.gameController.controlledCharacter.getLocation().y + 4f) / Constants.TILE_SIZE);
            System.out.println("to: " + x + ", " + y);
            
            List<PathFinderNode> path = algo.getPath(gameController._world.pathFinder.world[playerY][playerX], gameController._world.pathFinder.world[y][x]);
            if(path != null){
//                for(PathFinderNode node : path){
//                    //gameController._world.pathFinder.debugEntities[node.y][node.x].setColor(Color.MAGENTA);
//                    System.out.println("[" + node.x + "," + node.y + "]");
//                }
            //this.gameController.controlledCharacter.setPosition(path.get(0).x * Constants.TILE_SIZE + 8,path.get(0).x * Constants.TILE_SIZE);
            this.gameController.controlledCharacter.setCurrentPath(path, false);
            }

       return false; 
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return false;
    }
}
