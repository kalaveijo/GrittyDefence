package kalaveijo.game.gameobjects.playerunits;

import kalaveijo.game.gameobjects.Ai;
import kalaveijo.game.gameobjects.ObjectManager;
import kalaveijo.game.gameobjects.Tickable;
import kalaveijo.game.gameobjects.Unit;
import kalaveijo.game.util.Options;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Rifleman extends Unit implements Tickable {

	protected int health = 10;
	protected int speed = 2;
	protected int range = 2;
	protected int atkSpeed = 1;
	protected int status = IDLE;

	public Rifleman(long id, ObjectManager om) {
		super(id, om);
	}

	@Override
	public void loadBitmap(Bitmap m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Canvas c, Paint mPaint) {
		if (location != null) {
			mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setColor(Color.BLUE);
			c.drawCircle(location.x + offSetX, location.y + offSetY,
					Options.TILE_SIZE / 2, mPaint);
			// offSetX/Y are from superclass
		}
	}

	@Override
	public void move() {
		if (posX != -1) {
			// AI does decision making here
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
		this.ai = new Ai(getPosX(), getPosY(), getRange(), this);
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
					int spriteMoveAmount = Options.TILE_SIZE
							/ (Options.GAME_SPEED / speed);
					// else continnue moving
					actionLeft = actionLeft - speed;
					// calculate movement step amount for sprite
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
