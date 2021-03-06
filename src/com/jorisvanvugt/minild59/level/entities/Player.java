package com.jorisvanvugt.minild59.level.entities;

import java.awt.event.KeyEvent;

import com.jorisvanvugt.minild59.Input;
import com.jorisvanvugt.minild59.graphics.Bitmap;
import com.jorisvanvugt.minild59.graphics.SpriteManager;
import com.jorisvanvugt.minild59.models.Direction;

public class Player extends Entity {
	
	private Bitmap[] sprites;
	
	private static final int SPRITES_PER_ANIMATION = 3;
	private static final int FRAMES_PER_ANIMATION = 30;
	private Direction facing = Direction.SOUTH;
	private int animationState = 0;

	public Player() {
		super();
		x = 64 * 16 / 2;
		y = 64 * 16 / 2;
	}

	@Override
	public void update() {
		move();
		setSprite();
	}
	
	private void move() {
		int horInput = toInt(Input.getKeyDown(KeyEvent.VK_RIGHT)) - toInt(Input.getKeyDown(KeyEvent.VK_LEFT));
		int vertInput = toInt(Input.getKeyDown(KeyEvent.VK_DOWN)) - toInt(Input.getKeyDown(KeyEvent.VK_UP));
		if(horInput != 0 || vertInput != 0) {
			animationState++;
			// Make sure the player doesn't move sqrt(2) times as fast when moving diagnally by normalizing the input vector.
			double directionLength = Math.hypot(horInput, vertInput);
			double targetX = (double) horInput / directionLength;
			double targetY = (double) vertInput / directionLength;
			
			x += targetX;
			y += targetY;
			
			if(horInput == 1)
				facing = Direction.EAST;
			else if (horInput == -1)
				facing = Direction.WEST;
			else if(vertInput == 1)
				facing = Direction.SOUTH;
			else if(vertInput == -1) {
				facing = Direction.NORTH;
			}
		}

	}
	
	private void setSprite() {
		animationState %= FRAMES_PER_ANIMATION;
		sprite = sprites[animationState / (FRAMES_PER_ANIMATION / SPRITES_PER_ANIMATION) + SPRITES_PER_ANIMATION * facing.getValue()];
	}
	
	private int toInt(boolean b) {
		return b ? 1 : 0;
	}

	@Override
	public void draw(Bitmap bitmap) {
		bitmap.draw(sprite, Math.round(bitmap.getWidth() / 2 - sprite.getWidth() / 2), Math.round(bitmap.getHeight() / 2 - sprite.getHeight() / 2));
	}

	@Override
	public void loadSprite() {
		sprites = new Bitmap[12];
		sprites[0] = SpriteManager.CHAR1_FACING_UP1;
		sprites[1] = SpriteManager.CHAR1_FACING_UP2;
		sprites[2] = SpriteManager.CHAR1_FACING_UP3;
		sprites[3] = SpriteManager.CHAR1_FACING_RIGHT1;
		sprites[4] = SpriteManager.CHAR1_FACING_RIGHT2;
		sprites[5] = SpriteManager.CHAR1_FACING_RIGHT3;
		sprites[6] = SpriteManager.CHAR1_FACING_DOWN1;
		sprites[7] = SpriteManager.CHAR1_FACING_DOWN2;
		sprites[8] = SpriteManager.CHAR1_FACING_DOWN3;
		sprites[9] = SpriteManager.CHAR1_FACING_LEFT1;
		sprites[10] = SpriteManager.CHAR1_FACING_LEFT2;
		sprites[11] = SpriteManager.CHAR1_FACING_LEFT3;

		sprite = sprites[0];
		
	}

}
