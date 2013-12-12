package com.grenadelawnchair.games.sigmund.entity;

import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Sigmund implements Entity {

	private Body body;
	private final float MAX_SPEED = 5f;
	private Vector2 velocity = new Vector2(0, 0);
	private Vector2 currentVelocity = new Vector2(0, 0);
	private Sprite[] sprites;
	
	private AnimatedSprite strike, falling;
	
	/**
	 * Constructor for the Player Entity
	 */
	public Sigmund(World world, FixtureDef fixDef, float xPos, float yPos){
			
		sprites = new Sprite[2];
		initializeSprites();
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(xPos, yPos);
		
		fixDef.density = 5; // Should be around 1000
		fixDef.friction = 1;
		fixDef.restitution = .2f;
		
		// Body
		PolygonShape bodyShape = new PolygonShape();
		bodyShape.setAsBox(.5f, 1);
		fixDef.shape = bodyShape;
		body = world.createBody(bodyDef);
		body.createFixture(fixDef);
		body.setFixedRotation(true);
		body.setUserData(sprites[0]);
	}
	
	@Override
	public void update(){
		currentVelocity = body.getLinearVelocity();
		if(currentVelocity.x < MAX_SPEED && currentVelocity.x > -MAX_SPEED)
			body.applyForceToCenter(velocity, true);
		
		playAnimation();
	}
		
	@Override
	public Body getBody(){
		return body;
	}
	
	
	private void initializeSprites(){
		sprites[0] = new Sprite(new Texture(Gdx.files.internal("graphics/charactersprites/player/right/idle.png")));
		sprites[1] = new Sprite(new Texture(Gdx.files.internal("graphics/charactersprites/player/right/attack2.png")));

		for(int i = 0; i < sprites.length; i++){
			sprites[i].setSize(2, 2); // Size of body
		}
		
		// ANIMATIONS
		// Strike
		Animation animation = new Animation(1 / 10f, new TextureRegion(sprites[1]));
		animation.setPlayMode(Animation.LOOP);
		strike = new AnimatedSprite(animation, true);
		strike.setSize(2, 2);
		
		// Falling
		animation = new Animation(1 / 10f, new TextureRegion(sprites[0]));
		animation.setPlayMode(Animation.LOOP);
		falling = new AnimatedSprite(animation, true);
		falling.setSize(2, 2);
	
	}

	@Override
	public void dispose(){
//		for(int i = 0; i < sprites.length; i++){
//			sprites[0].getTexture().dispose();
//		}
	}

	private void playAnimation(){
		//TODO
	}
	
}
