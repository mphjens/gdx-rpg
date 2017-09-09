/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx.World;


import nl.vossnack.jensgdx.World.GameWorld;
import box2dLight.PointLight;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import nl.vossnack.jensgdx.Constants;

/**
 *
 * @author daemonaka
 */
public class MapBodyBuilder {

    // The pixels per tile. If your tiles are 16x16, this is set to 16f
    private static float ppt = 0;
    
    public static ArrayList<PointLight> buildPointLights(TiledMapTileLayer layer, GameWorld gameworld){
        int width = layer.getWidth();
        int height = layer.getHeight();
        
        for(int i = 0; i < height; i++){
            for( int j = 0; j < width; j++){
                Cell cell = layer.getCell(j, i);
                if(cell != null){
                    //cell.getTile().getProperties()
                }
            }
        }
        
        return null;
    }
    
    public static ArrayList<Body> buildTileShapes(TiledMapTileLayer layer,  HashMap<Integer,MapObject[]> shapeLibrary, World box2dWorld){
        ppt = Constants.TILE_SIZE;
        
        ArrayList<Body> bodies = new ArrayList<Body>();
        int width = layer.getWidth();
        int height = layer.getHeight();
        
        for(int i = 0; i < height; i++){
            for( int j = 0; j < width; j++){
                Cell c = layer.getCell(j, i);
                if(c != null){
                    TiledMapTile tile = c.getTile();
                    MapObject[] tileObjects = shapeLibrary.get(tile.getId());
                    if(tileObjects != null){
                        for(MapObject object : tileObjects) {

                            if (object instanceof TextureMapObject) {
                                continue;
                            }

                            Shape shape;
                            Vector2 offset = new Vector2(j * layer.getTileWidth(), (i + 1) * layer.getTileHeight());
                            if (object instanceof RectangleMapObject) {
                                shape = getRectangleForMapObject((RectangleMapObject)object, offset);
                            }
                            else if (object instanceof PolygonMapObject) {
                                shape = getPolygon((PolygonMapObject)object, offset);
                            }
                            else if (object instanceof PolylineMapObject) {
                                shape = getPolyline((PolylineMapObject)object, offset, false);
                            }
                            else if (object instanceof CircleMapObject) {
                                shape = getCircleForMapObject((CircleMapObject)object, offset);
                            }
                            else {
                                continue;
                            }

                            BodyDef bd = new BodyDef();
                            bd.type = BodyType.StaticBody;
                            Body body = box2dWorld.createBody(bd);
                            Fixture fixture = body.createFixture(shape, 1);

                            Filter collisionFilter = fixture.getFilterData();
                            collisionFilter.categoryBits = Constants.CollisionLayer.CATEGORY_OBSTACLE.getCode();

                            fixture.setFilterData(collisionFilter);

                            bodies.add(body);

                            shape.dispose();
                        }
                    }
                }
            }
        }
        
        return bodies;
    }

    public static ArrayList<Body> buildpolyShapes(MapLayer obstaclelayer, World box2dWorld, boolean triggers) {
        ppt = Constants.TILE_SIZE;
        MapObjects objects = obstaclelayer.getObjects();

        ArrayList<Body> bodies = new ArrayList<Body>();

        for(MapObject object : objects) {

            if (object instanceof TextureMapObject) {
                continue;
            }

            Shape shape;

            if (object instanceof RectangleMapObject) {
                shape = getRectangleForMapObject((RectangleMapObject)object, Vector2.Zero);
            }
            else if (object instanceof PolygonMapObject) {
                shape = getPolygon((PolygonMapObject)object, Vector2.Zero);
            }
            else if (object instanceof PolylineMapObject) {
                shape = getPolyline((PolylineMapObject)object, Vector2.Zero, true);
            }
            else if (object instanceof CircleMapObject) {
                shape = getCircleForMapObject((CircleMapObject)object, Vector2.Zero);
            }
            else {
                continue;
            }

            BodyDef bd = new BodyDef();
            bd.type = BodyType.StaticBody;
            Body body = box2dWorld.createBody(bd);
            Fixture fixture = body.createFixture(shape, 1);
            if(triggers)
                fixture.setSensor(true);
            Filter collisionFilter = fixture.getFilterData();
            if(object instanceof PolylineMapObject)
                collisionFilter.categoryBits = Constants.CollisionLayer.CATEGORY_BOUNDS.getCode();
            else
                collisionFilter.categoryBits = Constants.CollisionLayer.CATEGORY_OBSTACLE.getCode();
            
            fixture.setFilterData(collisionFilter);
            bodies.add(body);

            shape.dispose();
        }
        return bodies;
    }
    
    private static PolygonShape getRectangleForCell(int x, int y){
        float margin = (Constants.TILE_SIZE * 0.5f * (1.0f - Constants.CELL_OBSTACLE_SCALE));
        Rectangle rectangle = new Rectangle(
                x * Constants.TILE_SIZE + margin, 
                y * Constants.TILE_SIZE + margin, 
                Constants.TILE_SIZE * Constants.CELL_OBSTACLE_SCALE, 
                Constants.TILE_SIZE * Constants.CELL_OBSTACLE_SCALE);
        
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / ppt,
                                   (rectangle.y + rectangle.height * 0.5f ) / ppt);
        polygon.setAsBox(rectangle.width * 0.5f / ppt,
                         rectangle.height * 0.5f / ppt,
                         size,
                         0.0f);
        return polygon;
    }
    
    private static CircleShape getCircleForCell(int x, int y){
        float margin = (Constants.TILE_SIZE * (1.0f - Constants.CELL_OBSTACLE_SCALE));
        Circle circle = new Circle(x * Constants.TILE_SIZE + margin, y * Constants.TILE_SIZE + margin, Constants.TILE_SIZE * 0.5f * Constants.CELL_OBSTACLE_SCALE);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius / ppt);
        circleShape.setPosition(new Vector2(circle.x / ppt, circle.y / ppt));
        return circleShape;
    }

    private static PolygonShape getRectangleForMapObject(RectangleMapObject rectangleObject, Vector2 offset) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((offset.x + rectangle.x + rectangle.width * 0.5f) / ppt,
                                   (offset.y + rectangle.y + rectangle.height * 0.5f ) / ppt);
        polygon.setAsBox(rectangle.width * 0.5f / ppt,
                         rectangle.height * 0.5f / ppt,
                         size,
                         0.0f);
        return polygon;
    }

    private static CircleShape getCircleForMapObject(CircleMapObject circleObject, Vector2 offset) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius / ppt);
        circleShape.setPosition(new Vector2(offset.x + circle.x / ppt, circle.y / ppt));
        return circleShape;
    }

    private static PolygonShape getPolygon(PolygonMapObject polygonObject, Vector2 offset) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            //System.out.println(vertices[i]);
            worldVertices[i] = vertices[i] / ppt;
        }

        polygon.set(worldVertices);
        return polygon;
    }

    private static ChainShape getPolyline(PolylineMapObject polylineObject, Vector2 offset, boolean hasWorldTransform) {
        float[] vertices = hasWorldTransform ? polylineObject.getPolyline().getTransformedVertices() : polylineObject.getPolyline().getVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = (offset.x + vertices[i * 2]) / ppt;
            worldVertices[i].y = (offset.y + vertices[i * 2 + 1]) / ppt;
        }

        ChainShape chain = new ChainShape(); 
        chain.createChain(worldVertices);
        return chain;
    }
}
