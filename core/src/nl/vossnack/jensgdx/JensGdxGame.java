package nl.vossnack.jensgdx;

import com.badlogic.gdx.Game;
import nl.vossnack.jensgdx.screens.GameScreen;


public class JensGdxGame extends Game {

	
	@Override
	public void create () {
            setScreen(new GameScreen());
	}

	@Override
	public void render () {
            super.render();
	}
        
        @Override
        public void resize(int width, int height){
            super.resize(width, height);
        }
        
        @Override
        public void pause(){
            super.pause();
        }
        
        @Override
        public void resume(){
            super.resume();
        }
	
	@Override
	public void dispose () {
            super.dispose();
	}
}
