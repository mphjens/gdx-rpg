/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx.World;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;
import java.util.List;
import nl.vossnack.jensgdx.Constants;
import nl.vossnack.jensgdx.Entity;
import nl.vossnack.jensgdx.PhysicsEntity;
import nl.vossnack.jensgdx.Sprite;


/**
 *
 * @author Jens
 */
public class GameWorld implements ContactListener{
    public String mapFileName;
    public JGdxMap map;

    public World physWorld;
    public boolean drawDebug;
    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    
    public List<Entity> entityList;
    SpriteBatch entitySpriteBatch;
        
    public RayHandler lightingRayHandler;
    
    public OrthographicCamera camera;
    private Matrix4 scaledProjectionMatrix;

    public boolean isLoaded;
    private double timer = 0;
    
    public GameWorld(){        
        entityList = new ArrayList<Entity>();
        entitySpriteBatch = new SpriteBatch();
    }
    
    public void loadMap(String filename){
        if(this.map != null)
            this.unloadMap();
        
        isLoaded = false;
        mapFileName = filename;
        
        physWorld = new World(new Vector2(0,0), true);
        physWorld.setContactListener(this);
        
        RayHandler.setGammaCorrection(true);
        RayHandler.useDiffuseLight(true);
        lightingRayHandler = new RayHandler(physWorld);
        //lightingRayHandler.setShadows(false);
        lightingRayHandler.setAmbientLight(1f, 1f, 1f, 0.2f);
        lightingRayHandler.setBlurNum(0);
        lightingRayHandler.resizeFBO(1920, 1080);
        
        map = new JGdxMap();
        map.load(filename, this);
        
        camera = new OrthographicCamera();
        camera.zoom = 0.15f;
        
        scaledProjectionMatrix = new Matrix4(camera.combined);
               
        isLoaded = true;
        this.onResize(0, 0); 
    }
    
    public void unloadMap(){
        while(this.entityList.size() > 0){
            this.entityList.get(0).destroy();
        }
        this.physWorld.dispose();
        
        this.map.dispose();
        this.map = null;
        
        this.physWorld = null;
    }
    
    public void onResize(int width, int height){
        if(isLoaded){
            camera.viewportWidth = width;//Math.min(width, Constants.RESOLUTION_WIDTH);
            camera.viewportHeight = height;//Math.min(height, Constants.RESOLUTION_HEIGHT);;
            camera.update(true);
        }
    }
    
    private float physStepAccumulator = 0;
    private void stepPhysics(float deltatime, boolean fixed){
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        if(fixed){
            float frameTime = Math.min(deltatime, 0.25f);
            physStepAccumulator += frameTime;
            while (physStepAccumulator >= Constants.TIME_STEP) {
                physWorld.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
                physStepAccumulator -= Constants.TIME_STEP;
            }
        }
        else{
            physWorld.step(deltatime, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
        }
        
    }
    
    public void render(float deltatime){
        if(isLoaded){
            timer += deltatime;
            
            stepPhysics(deltatime, Constants.USE_FIXED_STEP_PHYS);
            
            camera.update();
            scaledProjectionMatrix.set(camera.combined).scl(Constants.PHYSICS_SCALE);
            
            this.map.renderBackgroundLayers(deltatime, camera);
            
            entitySpriteBatch.setProjectionMatrix(camera.combined);
            entitySpriteBatch.begin();
            for(int i = 0; i < entityList.size(); i++){
                  Entity e = entityList.get(i);

                  if(!e.isDestroyed()){
                    //todo: Maybe call entity updates in a seperate thread
                    e.update(deltatime);
                    for(Sprite s : e.getSprites())
                    {
                        s.render(entitySpriteBatch, deltatime);
                    }
                  } else {
                      entityList.remove(i);
                      i--;
                  }
            }
            entitySpriteBatch.end();

            this.map.renderForegroundLayers(deltatime, camera);

            if(drawDebug){
                debugRenderer.render(physWorld, scaledProjectionMatrix);
            }
            

            if(!this.map.inDoors){
                float DayCycle = (float)Math.abs(Math.cos( (timer/Constants.SECONDS_IN_DAY) * Math.PI ));
                lightingRayHandler.setAmbientLight(Math.max(0.1f, DayCycle), Math.max(0.1f, DayCycle * 0.8f), 1f, 0.2f);
            }
            
            lightingRayHandler.setCombinedMatrix(scaledProjectionMatrix, 0, 0, camera.viewportWidth, camera.viewportHeight);
            lightingRayHandler.updateAndRender();
            

        }
    }
    
    public void addEntity(Entity entity){
        if(entity != null && entityList.indexOf(entity) == -1){
            if(entity.name.isEmpty())
                entity.name = "entity_" + entityList.size();
            
            if(!entity.isLoaded()){
                System.out.println(entity.name + " was not loaded when added");
                entity.loadEntity();
                
            }
            
            entityList.add(entity);
            System.out.println(entity.name + " added to drawlist");
        }else{
            if(entity == null)
                System.out.println("tried adding null to drawlist");
            else
                System.out.println(entity.name + " already in drawlist");
        }
    }
    
    public void removeEntity(Entity entity){
        if(entity != null){
            entityList.remove(entity);
        }
        else{
            System.out.println("Tried to remove null entity");
        }
    }
    
    public PhysicsEntity getPhysicsEntityByBody(Body b){
        for(Entity e : entityList){
            
            if(e instanceof PhysicsEntity){
                PhysicsEntity pe = (PhysicsEntity)e;
                if(b == pe.getBody()){
                    return pe;
                }
            }
        }
        
        return null;
    }
    
    //Physics contact listener 
    @Override
    public void beginContact(Contact contact) {
        PhysicsEntity a = getPhysicsEntityByBody(contact.getFixtureA().getBody());
        PhysicsEntity b = getPhysicsEntityByBody(contact.getFixtureB().getBody());
        
        if(a != null || b != null)
        {
            if(a != null)
                a.onContactBegin(b, contact);
            if(b != null)
                b.onContactBegin(a, contact);
        }
        else{
            //trigger or level collisionboxes
        }
        
        

    }

    @Override
    public void endContact(Contact contact) {
        PhysicsEntity a = getPhysicsEntityByBody(contact.getFixtureA().getBody());
        PhysicsEntity b = getPhysicsEntityByBody(contact.getFixtureB().getBody());
        
        if(a != null || b != null)
        {
            if(a != null)
                a.onContactEnd(b, contact);
            if(b != null)
                b.onContactEnd(a, contact);
        }
        else{
            //trigger or level collisionboxes
            
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void dispose(){
        map.dispose();
        physWorld.dispose();
        entityList.clear();
        entitySpriteBatch.dispose();
    }
    
}
