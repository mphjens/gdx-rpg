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
public class Constants {
    public final static int RESOLUTION_WIDTH = 800;
    public final static int RESOLUTION_HEIGHT = 600;
    
    public final static boolean USE_FIXED_STEP_PHYS = false;
    public final static float TIME_STEP = 0.01f;
    public final static int VELOCITY_ITERATIONS = 6;
    public final static int POSITION_ITERATIONS = 2;
    
    public final static float TILES_PER_METER = 1.0f;
    public final static float TILE_SIZE = 16.0f;
    public final static float PHYSICS_SCALE = (float)TILE_SIZE / TILES_PER_METER;
    public final static float CELL_OBSTACLE_SCALE = 0.5f;
    
    public final static int LIGHT_RAY_COUNT = 128;
    public final static float SECONDS_IN_DAY = 100;
    
    public enum Direction{
        NORTH,
        EAST,
        SOUTH,
        WEST
    }
    
    public enum CollisionLayer{
        CATEGORY_NOCOLLISION((short)0x0000), // 0000000000000000 in binary
        CATEGORY_FRIENDLY((short)0x0001), // 0000000000000001 in binary
        CATEGORY_ENEMY((short)0x0002), // 0000000000000010 in binary
        CATEGORY_OBSTACLE((short)0x0004), // 0000000000000100 in binary
        CATEGORY_BOUNDS((short)0x0008), // 0000000000001000 in binary
        //Place new collision layers here..
        ;

            
        private final short val;
        private CollisionLayer(short val){
            this.val = val;
        }
        
        public short getCode(){
            return val;
        }
        
        public static short createMask(short ... categories){
            short retval = categories[0];
            for(int i = 1; i < categories.length; i++){
                retval = (short)(retval | categories[i]);
            }
            return retval;
        }
    }
}
