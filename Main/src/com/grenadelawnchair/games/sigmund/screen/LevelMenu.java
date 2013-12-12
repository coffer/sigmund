package com.grenadelawnchair.games.sigmund.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/**
 * This screen displays all the playable levels of the game 
 * that the player can choose from
 */
public class LevelMenu implements Screen {


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	}

	@Override
	public void resize(int width, int height) {
		// Nothing

	}

	@Override
	public void show() {
	
//		atlas = new TextureAtlas("ui/atlas.pack");
//		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlas);
		
	}

	@Override
	public void hide() {
		// Nothing

	}

	@Override
	public void pause() {
		// Nothing
	}

	@Override
	public void resume() {
		// Nothing
	}

	@Override
	public void dispose() {
		// Nothing
	}

}
