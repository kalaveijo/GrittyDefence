package kalaveijo.game.gameobjects;

import kalaveijo.game.engine.BitmapContainerGroup;
import kalaveijo.game.engine.Entity;
import kalaveijo.game.engine.Tickable;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.util.Options;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Unit extends Entity implements Tickable {

	public Unit(long id, ObjectManager om) {
		super(id, om);
		super.health = 10;
		super.speed = 1;
		super.range = 2;
		super.atkSpeed = 1;
		super.status = IDLE;
	}

	public Unit(long id, ObjectManager om, String name, int health, int speed,
			int range, int atkSpeed, String bitmapContainerGroup,
			BitmapContainerGroup bcg) {
		super(id, om);
		super.name = name;
		super.health = health;
		super.speed = speed;
		super.range = range;
		super.atkSpeed = atkSpeed;
		super.status = IDLE;
		super.bmContainerGroup = bcg;
	}

	public void loadAi(Ai ai) {
		this.ai = ai;
	}

	@Override
	public void loadBitmap(Bitmap m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Canvas c) {
		Bitmap picture = animator.animate();
		// if some reason we cant find correct bitmap
		if (picture == null) {
			if (location != null) {
				Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
				mPaint.setColor(Color.YELLOW);
				c.drawCircle(location.x + offSetX, location.y + offSetY,
						Options.TILE_SIZE / 2, mPaint);
				// offSetX/Y are from superclass
			}
		} else {
			c.drawBitmap(picture, location.x, location.y, new Paint());
		}
	}

	@Override
	public void move() {
		if (posX != -1) {
			// AI does decision making here
			if (ai != null)
				ai.assesAction();
			moveStatus();
		}
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void spawn(Point location, int x, int y) {
		this.location = location;
		this.posX = x;
		this.posY = y;
	}

	/*
	 * handles statemachine and counting
	 */
	protected void moveStatus() {
		if (status != IDLE) {
			switch (status) {

			case ATTACKING:

				// if done attacking
				if (actionLeft - atkSpeed < 0) {
					actionLeft = Options.GAME_SPEED;
					status = IDLE;
				} else {
					actionLeft = actionLeft - atkSpeed;
					// else continnue attacking
				}

				break;

			case MOVING:

				// if done moving
				if (actionLeft - speed < 0) {

					actionLeft = Options.GAME_SPEED;

					posX = nextTileX;
					posY = nextTileY;
					status = IDLE;
				} else {
					// calculate movement step amount for sprite
					int spriteMoveAmount = Options.TILE_SIZE
							/ (Options.GAME_SPEED / speed);
					// else continnue moving
					actionLeft = actionLeft - speed;

					// if moving to left
					if (nextTileX < posX) {
						location.x = location.x - spriteMoveAmount;
					} else if (nextTileX > posX) { // else right
						location.x = location.x + spriteMoveAmount;
					}

					// if moving up
					if (nextTileY < posY) {
						location.y = location.y - spriteMoveAmount;
					} else if (nextTileY > posY) { // else down
						location.y = location.y + spriteMoveAmount;
					}
				}

				break;

			case DYING:
				break;

			}
		} else {
			// insert sprite into correct place
			super.resetSpritePosition();
		}
	}

	// used to check if movement logic works
	public void debugOrder() {
		if (Options.DEBUG) {
			status = MOVING;
			nextTileX = 3;
			nextTileY = 4;
		}
	}

}
