/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vossnack.jensgdx.World;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import nl.vossnack.jensgdx.Constants;

/**
 *
 * @author Jens
 */
public class WorldLightProperties {
    public Vector2 offset = new Vector2(Constants.TILE_SIZE/2f, Constants.TILE_SIZE/2f);
    public Color color = Color.GOLDENROD;
    public float range = 3;
}
