package com.grenadelawnchair.games.sigmund.entity;

import com.badlogic.gdx.physics.box2d.Body;

public interface Entity {

	public void update();
	
	public Body getBody();

	public void dispose();
	
}
