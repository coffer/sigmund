package com.grenadelawnchair.games.sigmund.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.grenadelawnchair.games.sigmund.entity.Sigmund;

public class GameWorld implements Screen{

	private World world;
	private Box2DDebugRenderer debugRenderer;
	private float gravity = -9.81f;
	private OrthographicCamera camera;
	private final int ZOOM = 50;
	
	private final float TIMESTEP = 1 / 60f;
	private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3; // Can be set to higher if higher quality is desired
	
	private SpriteBatch batch;
	private Texture backgroundTexture;
	
	private Sigmund sigmund;
	private Array<Body> tmpBodies = new Array<Body>();
	private Sprite background;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		
		sigmund.update();
		
		camera.position.set(sigmund.getBody().getPosition().x, sigmund.getBody().getPosition().y, 0);
		camera.update();
		
		// Draw spites
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
//		background.draw(batch);
		batch.draw(backgroundTexture, -2, 0, Gdx.graphics.getWidth()/50, Gdx.graphics.getHeight()/50);
		
		world.getBodies(tmpBodies);
		for(Body body : tmpBodies){
			if(body.getUserData() != null && body.getUserData() instanceof Sprite){
				Sprite sprite = (Sprite) body.getUserData();
				sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2); // Draw sprite in bottom left
				sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
				sprite.draw(batch);
			}
		}	
		
		batch.end();
		
		debugRenderer.render(world, camera.combined);
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width / ZOOM;
		camera.viewportHeight = height / ZOOM;
		camera.update();
		
	}

	@Override
	public void show() {
		backgroundTexture = new Texture(Gdx.files.internal("graphics/background/bg.jpg"));
		
		world  = new World(new Vector2(0, gravity), true);
		debugRenderer = new Box2DDebugRenderer();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		
		// Input handling
		Gdx.input.setInputProcessor(new InputProcessor(){
				@Override
				public boolean scrolled(int amount) {
					camera.zoom += amount /25f;
					return true;
				}
				@Override
				public boolean keyUp(int keycode) {
					return false;
				}
				@Override
				public boolean keyTyped(char character) {
					return false;
				}
				@Override
				public boolean touchDown(int screenX, int screenY, int pointer,
						int button) {
					// TODO Auto-generated method stub
					return false;
				}
				@Override
				public boolean touchUp(int screenX, int screenY, int pointer,
						int button) {
					return false;
				}
				@Override
				public boolean touchDragged(int screenX, int screenY,
						int pointer) {
					return false;
				}
				@Override
				public boolean mouseMoved(int screenX, int screenY) {
					return false;
				}
				@Override
				public boolean keyDown(int keycode) {
					return false;
				}
		});

		// Setup World Objects
		FixtureDef fixDef = new FixtureDef();
		
		sigmund = new Sigmund(world, fixDef, 0, 2);
		
		// GROUND
		// Body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(0, 0);
				
		// Ground shape
		ChainShape groundShape = new ChainShape();
		groundShape.createChain(new Vector2[] {new Vector2(-100, 0), new Vector2(100, 0)});
		
		// Fixture definition
		fixDef.shape = groundShape;
		fixDef.friction = 1;
		fixDef.restitution = 0;
		
		world.createBody(bodyDef).createFixture(fixDef);
		
		groundShape.dispose();

	}

	@Override
	public void hide() {
		dispose();
		
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
		world.dispose();
		debugRenderer.dispose();
		sigmund.dispose();
	}
}
