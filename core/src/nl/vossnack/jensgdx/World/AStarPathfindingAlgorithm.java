/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx.World;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Jens
 */
public class AStarPathfindingAlgorithm extends PathfindingAlgorithm{
    
    private class NodeData{
        //G is the movement cost from the start point A to the current square.   
        //H is the estimated movement cost from the current square to the destination point (Heuristic)
        float F; //G + H
        PathFinderNode parent;
    }
    
    List<PathFinderNode> open;
    List<PathFinderNode> closed;
    HashMap<PathFinderNode, NodeData> nodeData;
    
    
    
    public AStarPathfindingAlgorithm(PathFinder pathfinder) {
        super(pathfinder);
        
        open = new ArrayList<PathFinderNode>();
        closed = new ArrayList<PathFinderNode>();
        
        nodeData = new HashMap<PathFinderNode, NodeData>();
    }

    @Override
    public List<PathFinderNode> getPath(PathFinderNode source, PathFinderNode dest) {
        open.clear();
        closed.clear();
        nodeData.clear();
        
        closed.add(source);
        PathFinderNode[] initialNeighbours = getNeighbours(source);
        for (PathFinderNode neighbour : initialNeighbours) {
            if(neighbour != null)
            {
                open.add(neighbour);
                NodeData d = new NodeData();
                d.F = neighbour.cost + findH(neighbour, dest);
                d.parent = source;
                nodeData.put(neighbour, d);
            }
        }
        
        while(!open.isEmpty()){
            PathFinderNode cNode = open.get(findClosestIdx());
            NodeData cData = nodeData.get(cNode);
            open.remove(cNode);
            closed.add(cNode);
            
            if(closed.contains(dest)){
                break; //Done!
            }
            
            PathFinderNode[] cNeighbours = getNeighbours(cNode);
            for (PathFinderNode neighbour : cNeighbours) {
                if(neighbour != null)
                {
                    if(closed.contains(neighbour)){
                        continue;
                    }

                    if(!open.contains(neighbour)){
                        open.add(neighbour);
                        NodeData d = new NodeData();
                        d.parent = cNode;
                        d.F = cData.F + neighbour.cost;
                        nodeData.put(neighbour, d);
                    }else{
                        float cScore = cData.F + neighbour.cost;
                        NodeData nD = nodeData.get(neighbour);
                        if(cScore < nD.F){
                            nD.F = cScore;
                            nD.parent = cNode; //Set current node as parent
                            
                            //Update current node.
                            //cData.F = cScore - neighbour.cost;
                        }
                    }
                }
            }
            
        }
        
        
        return tracepath(source, dest);
    }
    
    private List<PathFinderNode> tracepath(PathFinderNode source, PathFinderNode dest){
        List<PathFinderNode> retVal = new ArrayList<PathFinderNode>();
        
 
        PathFinderNode cParent = dest;
        retVal.add(dest);
        while(cParent != source){
            NodeData nd = nodeData.get(cParent);
            if(nd == null)
               return null;
               
          cParent = nd.parent;
           
           if(cParent != null){
               retVal.add(0,cParent);
           }
          else{
                return null; //No path could be reconstructed
           }
        }
        
        return retVal;
    }
    
    private int findClosestIdx(){
        float cMin = Float.MAX_VALUE;
        int minIdx = -1;
        for(int i = 0; i < open.size(); i++){
            NodeData d = nodeData.get(open.get(i));
            if(d.F < cMin){
                cMin = d.F;
                minIdx = i;
            }
        }
        
        return minIdx;
    }
    
    private float findH(PathFinderNode node, PathFinderNode dest){
        //Manhattan distance
        return Math.abs((node.x - dest.x)) + Math.abs((node.y - dest.y));
    }
    
    private PathFinderNode[] getNeighbours(PathFinderNode node){
        PathFinderNode north = null, northeast = null, east = null, southeast = null, south = null, southwest = null, west = null, northwest = null;
        if(node.y + 1 < pathfinder.world.length && !pathfinder.world[node.y + 1][node.x].occupied){
            north = pathfinder.world[node.y + 1][node.x];
            //north.cost = 1.0f;
        }
        
        if(node.y + 1 < pathfinder.world.length && node.x + 1 < pathfinder.world[0].length && !pathfinder.world[node.y + 1][node.x + 1].occupied){
            //northeast = pathfinder.world[node.y + 1][node.x + 1];
            //northeast.cost = 1.4f;
        }
        
        if(node.x + 1 < pathfinder.world[0].length && !pathfinder.world[node.y][node.x + 1].occupied){
            east = pathfinder.world[node.y][node.x + 1];
            //east.cost = 1.0f;
        }
            
        
        
        if(node.y - 1 >= 0 && node.x + 1 < pathfinder.world[0].length && !pathfinder.world[node.y - 1][node.x + 1].occupied){
            //southeast = pathfinder.world[node.y - 1][node.x + 1];
            //southeast.cost = 1.4f;
        }
        
        if(node.y - 1 >= 0 && !pathfinder.world[node.y - 1][node.x].occupied){
            south = pathfinder.world[node.y - 1][node.x];
            //south.cost = 1.0f;
        }
        
        if(node.y - 1 >= 0 && node.x - 1 >= 0 && !pathfinder.world[node.y - 1][node.x - 1].occupied){
            //southwest = pathfinder.world[node.y - 1][node.x - 1];
            //southwest.cost = 1.4f;
        }
        
        if(node.x - 1 >= 0 && !pathfinder.world[node.y][node.x - 1].occupied){
            west = pathfinder.world[node.y][node.x - 1];
            //west.cost = 1.0f;
        }
        
        if(node.y + 1 < pathfinder.world.length && node.x - 1 >= 0 && !pathfinder.world[node.y + 1][node.x - 1].occupied){
            //northwest = pathfinder.world[node.y + 1][node.x - 1];
            //northwest.cost = 1.4f;
        }
        
        
        
        
        
        return new PathFinderNode[] {north, northeast, east, southeast, south, southwest, west, northwest};
    }
    
}
