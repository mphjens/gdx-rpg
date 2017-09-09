/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import static java.lang.System.out;
import java.util.ArrayList;
import nl.vossnack.jensgdx.Characters.CharacterFactory;
import nl.vossnack.jensgdx.Characters.JgdxDebugCharacter;
import nl.vossnack.jensgdx.Characters.TestRpgCharacter;
import nl.vossnack.jensgdx.Characters.UniversalLpcSpriteMaleCharacter;
import nl.vossnack.jensgdx.Weapons.BoomerangWeapon;
import nl.vossnack.jensgdx.World.GameWorld;
import nl.vossnack.jensgdx.World.JGdxMap;
import nl.vossnack.jensgdx.screens.GameScreen;

/**
 *
 * @author Jens
 */
public class GameController{
    
    public GameScreen _screen;
    public OrthographicCamera _mainCamera;
    public GameWorld _world;
    
    float cameraSpeed = 2f;
    Vector2 charDirection;
    public CharacterEntity controlledCharacter;
    CharacterControls controlContext;
    
    double timer = 0;
    
    public GameController(GameScreen screen)
    {
        _screen = screen;
        _world = screen.world;
        
        //_world.loadWorld("maps/tavern.tmx");
        for(int i = 0; i < 1000; i++)
            _world.loadMap("maps/atlasmap_tilecollision_test.tmx");
        
        
        _mainCamera = screen.world.camera;
        
        charDirection = new Vector2();
        
        controlledCharacter = CharacterFactory.Create(screen.world, UniversalLpcSpriteMaleCharacter.class, _world.map.spawnPoints.get(0));
        controlledCharacter.equipWeapon(new BoomerangWeapon());
        
        controlContext = new CharacterControls();
        Gdx.input.setInputProcessor(controlContext);
    }
    
    Vector3 vCamPos = new Vector3();
    Vector3 camDest = new Vector3();
    public void update(float deltatime){
        timer += deltatime;
        
        camDest.set(controlledCharacter.getPosition().x, controlledCharacter.getPosition().y, _mainCamera.position.z);
        vCamPos.lerp(camDest, deltatime * cameraSpeed);
        
        _screen.world.drawDebug = controlContext.SHOW_DEBUG;
        
  
        _mainCamera.position.x = MathUtils.round(1000f * vCamPos.x) / 1000f;
        _mainCamera.position.y = MathUtils.round(1000f * vCamPos.y) / 1000f;
        
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
