/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx.World;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.utils.Disposable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import nl.vossnack.jensgdx.Constants;

/**
 *
 * @author Jens
 */
public class JGdxMap implements Disposable{
    public TiledMap tiledMap;
    private OrthogonalTiledMapRenderer renderer;
    
    public int Width;
    public int Height;
    
    int[] bgLayerIdxs;
    int[] foregroundLayerIdxs;
    
    public final ArrayList<Vector2> spawnPoints;
    public boolean inDoors = false;
    
    CustomAtlasTmxMapLoader loader;
    
    public JGdxMap(){
        spawnPoints = new ArrayList<Vector2>();
    }
    
    public void load(String filename, GameWorld world){
        // TmxMapLoader.Parameters
        loader = new CustomAtlasTmxMapLoader();
        tiledMap = loader.load(filename);
        //Set Map properties here
        
        Width = tiledMap.getProperties().get("width", Integer.class);
        Height = tiledMap.getProperties().get("height", Integer.class);
        this.inDoors = tiledMap.getProperties().containsKey("indoors");
        
        Iterator<String> keys = tiledMap.getProperties().getKeys();
        while(keys.hasNext()){
            String key = keys.next();
            
            if(key.startsWith("spawn")){
                String[] comps = tiledMap.getProperties().get(key, String.class).split(",");
                int x = (int)(Integer.parseInt(comps[0]) * Constants.TILE_SIZE);
                int y = (int)((Height - Integer.parseInt(comps[1])) * Constants.TILE_SIZE);
                this.spawnPoints.add(new Vector2(x,y));
            }
        }
        
        
        
        addTileLightsToWorld(world);

        List<Integer> bgIdxList = new ArrayList<Integer>();
        List<Integer> foregroundIdxList = new ArrayList<Integer>();
        
        int idx = -1;
        for(MapLayer layer : tiledMap.getLayers())
        {
            idx++;
            
            String name = layer.getName();
            if(name.startsWith("bg")){
                bgIdxList.add(idx);
                continue;
            }
            if(name.startsWith("obstacles")){
                TiledMapTileLayer tileLayer = (TiledMapTileLayer)layer;
                
                MapBodyBuilder.buildEntities(tileLayer, loader.tilesetCollisionObjects, world);
                continue;
            }
            if(name.startsWith("polyobstacles")){
                MapBodyBuilder.buildpolyShapes(layer, world.physWorld, false);
                continue;
            }
            
            if(name.startsWith("triggers")){
                MapBodyBuilder.buildpolyShapes(layer, world.physWorld, true);
                continue;
            }
            
            foregroundIdxList.add(idx);
        }
        
        this.bgLayerIdxs = new int[bgIdxList.size()];
        for(int i = 0; i < bgIdxList.size(); i++)
        {
            bgLayerIdxs[i] = bgIdxList.get(i);
        }
        
        this.foregroundLayerIdxs = new int[foregroundIdxList.size()];
        for(int i = 0; i < foregroundIdxList.size(); i++)
        {
            foregroundLayerIdxs[i] = foregroundIdxList.get(i);
        }
            
        renderer = new OrthogonalTiledMapRenderer(this.tiledMap, world.worldSpriteBatch);
    }
    

    public void addTileLightsToWorld(GameWorld world){
        //Loop door de tiles in alle layers en voeg een pointlight
        //toe op alle tiles met een tileid in loader.tileLights.
        //zie MapBodyBuilder voor voorbeeld van het loopen door de tiles
        for(MapLayer l : this.tiledMap.getLayers()){
            if(l instanceof TiledMapTileLayer){
                TiledMapTileLayer layer = (TiledMapTileLayer)l;
                
                for(int i = 0; i < Height; i++){
                    for( int j = 0; j < Width; j++){
                        TiledMapTileLayer.Cell c = layer.getCell(j, i);
                        if(c != null){
                            WorldLightProperties lightproperties = loader.tilesetLights.get(c.getTile().getId());
                            if(lightproperties != null){
                                Vector2 pos = new Vector2((j * Constants.TILE_SIZE) + lightproperties.offset.x, ((i) * Constants.TILE_SIZE) + lightproperties.offset.y).scl(1/Constants.PHYSICS_SCALE);
                                PointLight light = new PointLight(world.lightingRayHandler, Constants.LIGHT_RAY_COUNT, Color.GOLDENROD, lightproperties.range, pos.x, pos.y);
                                
                                light.setStaticLight(true);
                                Filter f = new Filter();
                                f.categoryBits = Constants.CollisionLayer.CATEGORY_FRIENDLY.getCode();
                                f.maskBits = Constants.CollisionLayer.createMask(Constants.CollisionLayer.CATEGORY_ENEMY.getCode(),
                                                                                Constants.CollisionLayer.CATEGORY_OBSTACLE.getCode());
                                light.setContactFilter(f);
                            }
                        }                        
                    }
                }
            }
            
        }
        
        
    }
    
    public void renderBackgroundLayers(float deltatime, OrthographicCamera camera){
        renderer.setView(camera);
        renderer.render(bgLayerIdxs);
    }
    
    public void renderForegroundLayers(float deltatime, OrthographicCamera camera){
        renderer.render(foregroundLayerIdxs);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        tiledMap.dispose();
        spawnPoints.clear();
    }
    
    
}
