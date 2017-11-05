/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx.World;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import java.util.ArrayList;
import java.util.List;
import nl.vossnack.jensgdx.Constants;
import nl.vossnack.jensgdx.PrimitiveEntity;

/**
 *
 * @author Jens
 */
public class PathFinder implements Disposable{
    
    GameWorld gameWorld;
    public PathFinderNode[][] world;
    public PrimitiveEntity[][] debugEntities;
    
    Vector2 worldSize;
    
    public PathFinder(GameWorld gameWorld){
        this.gameWorld = gameWorld;
        this.worldSize = new Vector2(gameWorld.map.Width, gameWorld.map.Height);
    }
    
    public List<Vector2> FindPath(Vector2 start, Vector2 end){
        List<Vector2> path = new ArrayList<Vector2>();
        
        
        return path;
    }
    
    public void generateWorld(){
        int nNodesX = (int)(worldSize.x);
        int nNodesY = (int)(worldSize.y);
        final float margin = 0.1f;
        
        
        this.world = new PathFinderNode[nNodesY][nNodesX];
        this.debugEntities = new PrimitiveEntity[nNodesY][nNodesX];
        for(int cY = 0; cY < nNodesY; cY++ ){
            for(int cX = 0; cX < nNodesX; cX++ ){
                final PathFinderNode node = new PathFinderNode();
                node.x = cX;
                node.y = cY;
                
                world[cY][cX] = node;
                
                
//                final PrimitiveEntity square = new PrimitiveEntity(PrimitiveEntity.PrimitiveType.Square, new Color(0, 1, 1, 0.5f), (1 - (2*margin)) * Constants.PHYSICS_SCALE, (1 - (2*margin)) * Constants.PHYSICS_SCALE);
//                square.setPosition(new Vector2((node.x + margin) * Constants.PHYSICS_SCALE, ( node.y + margin) * Constants.PHYSICS_SCALE));
//                square.addToWorld(PathFinder.this.gameWorld);
//                this.debugEntities[cY][cX] = square;
                
                this.gameWorld.physWorld.QueryAABB(new QueryCallback(){
                    @Override
                    public boolean reportFixture(Fixture fixture) {
                        node.occupied = true;
                        //square.setColor(new Color(1, 0, 0, 0.5f));
                        //System.out.println("Found Fixture at : " + node.x + " " + node.y + " " + gameWorld.getPhysicsEntityByBody(fixture.getBody()));
                        return false;
                    }
                }, cX + margin, (cY) + margin, (cX + margin) + (1 - (2 *margin)) , (cY + margin) + (1 - (2 *margin)));
                
                
            }
        }
    }
    
    public String worldToString(){
        int nNodesX = (int)(worldSize.x);
        int nNodesY = (int)(worldSize.y);
        
        String retval = "";
        for(int cY = 0; cY < nNodesY; cY++ ){
            for(int cX = 0; cX < nNodesX; cX++ ){
                if(world[cY][cX].occupied)
                    retval += "X";
                else
                    retval += "_";
            }
            
            retval += "\n";
        }
        
        return retval;
    }

    @Override
    public void dispose() {
        this.world = null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
