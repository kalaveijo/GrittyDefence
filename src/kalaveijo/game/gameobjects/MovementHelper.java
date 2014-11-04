package kalaveijo.game.gameobjects;

import kalaveijo.game.engine.BitmapContainer;
import kalaveijo.game.engine.Entity;
import kalaveijo.game.engine.Tickable;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.util.Options;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

/*
 * Class under map that tells enemy units where they can move next
 * Each helper only points into certain direction
 */
public class MovementHelper extends Entity implements Tickable {

	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int UPLEFT = 4;
	public static final int DOWNLEFT = 5;
	public static final int UPDOWNLEFT = 6;

	private int direction;

	public MovementHelper(long id, ObjectManager om, Point location) {
		super(id, om);
		direction = RIGHT;
		this.location = location;
		this.name = "MapTile";
	}

	public MovementHelper(long id, ObjectManager om, int direction,
			Point location) {
		super(id, om);
		this.direction = direction;
		this.location = location;
	}

	/*
	 * public void draw(Canvas canvas) {
	 * 
	 * Paint paint = new Paint(); // helpers will only be drawn when debug is
	 * tagged on if (Options.DEBUG) { if (picture != null) { if (direction ==
	 * UP) { canvas.drawBitmap(picture[UP], location.x, location.y, paint); }
	 * 
	 * if (direction == DOWN) { canvas.drawBitmap(picture[DOWN], location.x,
	 * location.y, paint); }
	 * 
	 * if (direction == LEFT) { canvas.drawBitmap(picture[LEFT], location.x,
	 * location.y, paint); }
	 * 
	 * if (direction == RIGHT) { canvas.drawBitmap(picture[RIGHT], location.x,
	 * location.y, paint); } } } }
	 */

	public void draw(Canvas canvas) {
		Paint paint = new Paint();

		try {
			if (Options.DEBUG) {
				if (bmContainerGroup != null) {
					// write nullchecks you shit
					// lol no
					if (direction == UP) {
						canvas.drawBitmap(bmContainerGroup
								.findBitmapContainerByType(BitmapContainer.UP)
								.getPicture(), location.x, location.y, paint);
					}

					if (direction == DOWN) {
						canvas.drawBitmap(
								bmContainerGroup.findBitmapContainerByType(
										BitmapContainer.DOWN).getPicture(),
								location.x, location.y, paint);
					}

					if (direction == LEFT) {
						canvas.drawBitmap(
								bmContainerGroup.findBitmapContainerByType(
										BitmapContainer.LEFT).getPicture(),
								location.x, location.y, paint);
					}

					if (direction == RIGHT) {
						canvas.drawBitmap(
								bmContainerGroup.findBitmapContainerByType(
										BitmapContainer.RIGHT).getPicture(),
								location.x, location.y, paint);
					}
					
					if (direction == UPLEFT) {
						canvas.drawBitmap(
								bmContainerGroup.findBitmapContainerByType(
										BitmapContainer.UPLEFT).getPicture(),
								location.x, location.y, paint);
					}
					if (direction == DOWNLEFT) {
						canvas.drawBitmap(
								bmContainerGroup.findBitmapContainerByType(
										BitmapContainer.DOWNLEFT).getPicture(),
								location.x, location.y, paint);
					}
					if (direction == UPDOWNLEFT) {
						canvas.drawBitmap(
								bmContainerGroup.findBitmapContainerByType(
										BitmapContainer.UPDOWNLEFT).getPicture(),
								location.x, location.y, paint);
					}
				}
			}
		} catch (Exception e) {
			Log.d("MovementHelperException", e.toString());
		}

	}

	public int getDirection() {
		return this.direction;
	}

	// loads all pictures
	public void loadBitmap(Bitmap[] bm) {
		this.picture = bm;
	}

	public static int parseDirection(String direction) {
		int convertedDirection = 0;
		if (direction.equals("up")) {
			convertedDirection = UP;
		}
		if (direction.equals("down")) {
			convertedDirection = DOWN;
		}

		if (direction.equals("left")) {
			convertedDirection = LEFT;
		}

		if (direction.equals("right")) {
			convertedDirection = RIGHT;
		}
		if (direction.equals("upleft")) {
			convertedDirection = UPLEFT;
		}
		if (direction.equals("downleft")) {
			convertedDirection = DOWNLEFT;
		}
		if (direction.equals("updownleft")) {
			convertedDirection = UPDOWNLEFT;
		}

		return convertedDirection;
	}
}
