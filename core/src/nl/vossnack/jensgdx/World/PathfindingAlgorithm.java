/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx.World;

import com.badlogic.gdx.math.Vector2;
import java.util.List;

/**
 *
 * @author Jens
 */
public abstract class PathfindingAlgorithm {
    PathFinder pathfinder;
    //PathFinderNode[][] world;
    
    public PathfindingAlgorithm(PathFinder pathfinder){
        this.pathfinder = pathfinder;
    }
    
    public abstract List<PathFinderNode> getPath(PathFinderNode source, PathFinderNode dest);
}
