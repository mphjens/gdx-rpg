/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx.Characters;

import com.badlogic.gdx.math.Vector2;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.vossnack.jensgdx.CharacterEntity;
import nl.vossnack.jensgdx.World.GameWorld;
import nl.vossnack.jensgdx.Weapons.BoomerangWeapon;

/**
 *
 * @author Jens
 */
public class CharacterFactory {
    public static CharacterEntity Create(GameWorld world, Class characterEntityClass, Vector2 position){
        CharacterEntity character;
        try {
            character = (CharacterEntity)(characterEntityClass.newInstance());
        } catch (InstantiationException ex) {
            Logger.getLogger(CharacterFactory.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CharacterFactory.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        character.loadEntity();
        character.addToWorld(world);
        character.setPosition(position);
        
                
        return character;
    }
}
