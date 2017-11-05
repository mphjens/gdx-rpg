/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ai.steer.behaviors.BlendedSteering;
import com.badlogic.gdx.ai.steer.behaviors.Hide;
import com.badlogic.gdx.ai.steer.behaviors.LookWhereYouAreGoing;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.ai.steer.behaviors.RaycastObstacleAvoidance;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.ai.steer.proximities.InfiniteProximity;
import com.badlogic.gdx.ai.steer.utils.rays.CentralRayWithWhiskersConfiguration;
import com.badlogic.gdx.ai.steer.utils.rays.RayConfigurationBase;
import com.badlogic.gdx.ai.steer.utils.rays.SingleRayConfiguration;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;
import java.util.List;
import nl.vossnack.jensgdx.Characters.CharacterFactory;
import nl.vossnack.jensgdx.Characters.UniversalLpcSpriteMaleCharacter;
import nl.vossnack.jensgdx.Weapons.BoomerangWeapon;
import nl.vossnack.jensgdx.Weapons.DebugSwordWeapon;
import nl.vossnack.jensgdx.World.Box2dRaycastCollisionDetector;
import nl.vossnack.jensgdx.World.GameWorld;
import nl.vossnack.jensgdx.screens.GameScreen;

/**
 *
 * @author Jens
 */
public class GameController{
    
    public GameScreen _screen;
    public OrthographicCamera _mainCamera;
    public GameWorld _world;
    public InputMultiplexer _inputMultiplexer;
    
    float cameraSpeed = 2f;
    Vector2 charDirection; //TODO: Move all logic using this var to the character class and pass controlContext to it.
    public CharacterEntity controlledCharacter;
    CharacterControls controlContext;
    
    ShapeRenderer shapeRenderer;
    RayConfigurationBase<Vector2>[] aiRayConfigs;
    Box2dRaycastCollisionDetector aiRaycastDetector;
    Wander<Vector2> DebugDrawnWanderSB;
    
    List<CharacterEntity> players;
    List<CharacterEntity> npcs;
    
    double timer = 0;
    
    public GameController(GameScreen screen)
    {
        _screen = screen;
        _world = screen.world;
        
        //_world.loadWorld("maps/tavern.tmx");
        //for(int i = 0; i < 1000; i++)
        _world.loadMap("maps/newmap.tmx");
                
        PrimitiveEntity square = new PrimitiveEntity(PrimitiveEntity.PrimitiveType.Circle, Color.RED, 4f, 4f);
        square.setPosition(new Vector2(16, 16));
        square.addToWorld(_world);
        
        _mainCamera = screen.world.camera;
        
        players = new ArrayList<CharacterEntity>();
        npcs = new ArrayList<CharacterEntity>();
        
        charDirection = new Vector2();
        controlledCharacter = CharacterFactory.Create(screen.world, UniversalLpcSpriteMaleCharacter.class, _world.map.spawnPoints.get(0));
        //controlledCharacter.equipWeapon(new BoomerangWeapon());
        CharacterWeaponStats weaponStats = new CharacterWeaponStats();
        controlledCharacter.equipWeapon(new DebugSwordWeapon(weaponStats, controlledCharacter));
        controlledCharacter.stats.speed = 2f;
        players.add(controlledCharacter);
        
        aiRayConfigs = new RayConfigurationBase[1];
        aiRaycastDetector =  new Box2dRaycastCollisionDetector(this._world.physWorld);
        shapeRenderer = new ShapeRenderer();
        
        for(int i = 0; i < 1; i++){
            CharacterEntity steeredCharacter = CharacterFactory.Create(screen.world, UniversalLpcSpriteMaleCharacter.class, _world.map.spawnPoints.get(0));
            
            steeredCharacter.setMaxLinearAcceleration(steeredCharacter.stats.speed * 100f);
            steeredCharacter.setMaxLinearSpeed(steeredCharacter.stats.speed);
            
            
            Wander<Vector2> wanderSB = new Wander<Vector2>(steeredCharacter) //
                    .setFaceEnabled(false) // We want to use Face internally (independent facing is on)
                    .setWanderOffset(3f) //
                    .setDecelerationRadius(1f)
                    .setTimeToTarget(0.1f)
                    .setWanderOrientation(1f)
                    .setWanderRadius(3F) //
                    .setWanderRate(1.14f );
            
            //aiRayConfigs[i] = new SingleRayConfiguration<Vector2>(steeredCharacter, 4.3f);
            aiRayConfigs[i] = new CentralRayWithWhiskersConfiguration<Vector2>(steeredCharacter, 3f,
				1.2f, 25 * MathUtils.degreesToRadians);
            RaycastObstacleAvoidance obsAvoid = new RaycastObstacleAvoidance<Vector2>(steeredCharacter, aiRayConfigs[i], aiRaycastDetector);
            obsAvoid.setDistanceFromBoundary(1f);
            
            
//            InfiniteProximity<Vector2> infProximity = new InfiniteProximity<Vector2>(steeredCharacter, this.players);
//            Hide hideSB = new Hide<Vector2>(steeredCharacter, target, infProximity) //
//                .setDistanceFromBoundary(DISTANCE_FROM_BOUNDARY) //
//                .setTimeToTarget(0.1f) //
//                .setArrivalTolerance(0.001f) //
//                .setDecelerationRadius(80);
            
            BlendedSteering<Vector2> prioritySteeringSB = new BlendedSteering<Vector2>(steeredCharacter) //
			.add(obsAvoid, 1.1f) //
			.add(wanderSB, 1f);
            
            
            //steeredCharacter.steeringBehavior = prioritySteeringSB;
            if(DebugDrawnWanderSB == null)
                DebugDrawnWanderSB = wanderSB;
            npcs.add(steeredCharacter);
        }
        
        
        controlContext = new CharacterControls();
        
        _inputMultiplexer = new InputMultiplexer();
        _inputMultiplexer.addProcessor(controlContext);
        _inputMultiplexer.addProcessor(new DebugInputProcessor(this));
        Gdx.input.setInputProcessor(_inputMultiplexer);
    }
    
    Vector3 vCamPos = new Vector3();
    Vector3 camDest = new Vector3();
    Vector2 tmp = new Vector2();
    Vector2 tmp2 = new Vector2();
    public void update(float deltatime){
        timer += deltatime;
        
        camDest.set(controlledCharacter.getLocation().x, controlledCharacter.getLocation().y, _mainCamera.position.z);
        vCamPos.lerp(camDest, deltatime * cameraSpeed);
        
        
        //Todo: move this someplace else like a 'NPC manager' or something.
        //Draw the rays cast by the ObstacleAvoidance Steering behaviour
        if(this.controlContext.SHOW_DEBUG)
        {
            shapeRenderer.begin(ShapeType.Line);
            shapeRenderer.setColor(1, 0, 0, 1);
            for(int i = 0; i < this.aiRayConfigs.length; i++){
            Ray<Vector2>[] rays = this.aiRayConfigs[i].getRays();	
                shapeRenderer.setProjectionMatrix(this._world.scaledProjectionMatrix);
                for (Ray<Vector2> ray : rays) {
                    tmp.set(ray.start);

                    tmp2.set(ray.end);
                    shapeRenderer.line(tmp, tmp2);
                }
            }
            shapeRenderer.end();
            
            if(DebugDrawnWanderSB != null){
                shapeRenderer.begin(ShapeType.Filled);
                
                shapeRenderer.setColor(1, 1, 0, 1);
                shapeRenderer.box(DebugDrawnWanderSB.getWanderCenter().x,DebugDrawnWanderSB.getWanderCenter().y, 0f, 0.1f, 0.1f, 1f);
                
                shapeRenderer.setColor(1, 0, 1, 1);
                shapeRenderer.box(DebugDrawnWanderSB.getInternalTargetPosition().x,DebugDrawnWanderSB.getInternalTargetPosition().y, 0f, 0.1f, 0.1f, 1f);
                
                shapeRenderer.end();
            }
        }
        
        
        
        _screen.world.drawDebug = controlContext.SHOW_DEBUG;
        
        _mainCamera.position.x = MathUtils.clamp(MathUtils.round(1000f * vCamPos.x) / 1000f, 0, (this._world.map.Width * Constants.TILE_SIZE) + (this._world.camera.viewportWidth / 2f));
        _mainCamera.position.y = MathUtils.clamp(MathUtils.round(1000f * vCamPos.y) / 1000f, 0, (this._world.map.Height * Constants.TILE_SIZE) + (this._world.camera.viewportHeight / 2f));
        
        if(!(controlContext.MOVE_UP && controlContext.MOVE_DOWN)){
            if(controlContext.MOVE_UP)
                this.charDirection.y = 1;
            else if(controlContext.MOVE_DOWN)
                this.charDirection.y = -1;
            else
                this.charDirection.y = 0;
        }
        else{
            this.charDirection.y = 0;
        }
        
        if(!(controlContext.MOVE_LEFT && controlContext.MOVE_RIGHT)){
            if(controlContext.MOVE_RIGHT)
                this.charDirection.x = 1;
            else if(controlContext.MOVE_LEFT)
                this.charDirection.x = -1;
            else
                this.charDirection.x = 0;
        }
        else{
            this.charDirection.x = 0;
        }
        
        if(controlContext.ATTACK){
            controlledCharacter.fireWeapon(0);
        }
        
        controlledCharacter.move(charDirection);
    }
}
