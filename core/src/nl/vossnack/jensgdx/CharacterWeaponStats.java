/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx;

/**
 *
 * @author Jens
 */
public class CharacterWeaponStats {
    public String name;
    public WeaponTypes weapontype;
    public float damage = 10f, range = 0.5f, speed = 1f;
    
    public enum WeaponTypes{
        ONE_HANDED_MELEE,
        TWO_HANDED_MELEE,
        ONE_HANDED_RANGED,
        TWO_HANDED_RANGED,
    }
}
