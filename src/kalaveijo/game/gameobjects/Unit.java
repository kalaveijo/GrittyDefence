package kalaveijo.game.gameobjects;

import kalaveijo.game.util.Options;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

/*
 * Basic superclass for game unit
 */
public class Unit implements Tickable {

	public static final int IDLE = 0;
	public static final int MOVING = 1;
	public static final int ATTACKING = 2;
	public static final int DYING = 3;

	protected int offSetX = Options.TILE_SIZE / 2,
			offSetY = Options.TILE_SIZE / 2;
	protected int health = 0;
	protected long id;
	protected Ai ai = null;
	protected Point location = null;
	protected int posX, posY = -1;
	protected int range = 0;
	protected int speed = 0;
	protected int atkSpeed = 0;
	protected int status = IDLE;
	protected int actionLeft = Options.GAME_SPEED;
	protected int nextTileX = 0;
	protected int nextTileY = 0;
	protected Bitmap[] picture;
	protected ObjectManager om;

	public Unit(long id, ObjectManager om) {
		this.id = id;
		this.om = om;
	}

	public int getPosX() {
		return this.posX;
	}

	public int getPosY() {
		return this.posY;
	}

	@Override
	public void loadBitmap(Bitmap m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Canvas c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void spawn(Point location, int x, int y) {
		// TODO Auto-generated method stub

	}

	public int getRange() {
		return this.range;
	}

	public int getSpeed() {
		return this.speed;
	}

	public int getStatus() {
		return this.status;
	}

	public int getAtkSpeed() {
		return this.status;
	}

	public int getActionLeft() {
		return this.actionLeft;
	}

	public int getNextTileX() {
		return this.nextTileX;
	}

	public int getNextTileY() {
		return this.nextTileY;
	}

	public ObjectManager getObjectManager() {
		return this.om;
	}

}
