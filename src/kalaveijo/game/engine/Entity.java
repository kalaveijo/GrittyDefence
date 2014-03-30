package kalaveijo.game.engine;

import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.gameobjects.Ai;
import kalaveijo.game.util.MapLocation;
import kalaveijo.game.util.Options;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

/*
 * Basic superclass for game unit
 */
public class Entity implements Tickable {

	public static final int IDLE = 0;
	public static final int MOVING = 1;
	public static final int ATTACKING = 2;
	public static final int DYING = 3;

	public static final int UP = 0;
	public static final int UP_RIGHT = 1;
	public static final int RIGHT = 2;
	public static final int DOWN_RIGHT = 3;
	public static final int DOWN = 4;
	public static final int DOWN_LEFT = 5;
	public static final int LEFT = 6;
	public static final int UP_LEFT = 7;

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
	protected BitmapContainerGroup bmContainerGroup;
	protected String bitmapContainerGroup;
	protected String name;
	protected ObjectManager om;
	protected Animator animator;
	protected int currentDirection = RIGHT;

	public BitmapContainerGroup getBmContainerGroup() {
		return bmContainerGroup;
	}

	public void setBmContainerGroup(BitmapContainerGroup bmContainerGroup) {
		this.bmContainerGroup = bmContainerGroup;
	}

	public Entity(long id, ObjectManager om) {
		this.id = id;
		this.om = om;
		this.animator = new Animator(this);
	}

	// used when making empty templates
	public Entity() {
		this.animator = new Animator(this);
	}

	public int getPosX() {
		return this.posX;
	}

	public int getPosY() {
		return this.posY;
	}

	public void setPosX(int i) {
		this.posX = i;
	}

	public void setPosY(int i) {
		this.posY = i;
	}

	public void setNextTileX(int i) {
		this.nextTileX = i;
	}

	public void setNextTileY(int i) {
		this.nextTileY = i;
	}

	public void setStatus(int i) {
		this.status = i;
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

	// resets sprite to corresponds current x,y position
	public void resetSpritePosition() {
		location.x = posX * Options.TILE_SIZE;
		location.y = posY * Options.TILE_SIZE;
	}

	public Point getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public String getBitmapContainerGroup() {
		return bitmapContainerGroup;
	}

	public int getCurrentDirection() {
		return currentDirection;
	}

	public static int calculateDirection(MapLocation startPoint,
			MapLocation endPoint) {
		int direction = RIGHT;
		int a = startPoint.y - endPoint.y;
		int b = startPoint.x - startPoint.x;

		return direction;
	}

}
