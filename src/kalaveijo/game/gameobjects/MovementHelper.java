package kalaveijo.game.gameobjects;

import kalaveijo.game.engine.Entity;
import kalaveijo.game.engine.ObjectManager;
import kalaveijo.game.engine.Tickable;
import kalaveijo.game.util.Options;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/*
 * Class under map that tells enemy units where they can move next
 * Each helper only points into certain direction
 */
public class MovementHelper extends Entity implements Tickable {

	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;

	private int direction;

	public MovementHelper(long id, ObjectManager om, Point location) {
		super(id, om);
		direction = RIGHT;
		this.location = location;
	}

	public MovementHelper(long id, ObjectManager om, int direction,
			Point location) {
		super(id, om);
		this.direction = direction;
		this.location = location;
	}

	public void draw(Canvas canvas) {

		Paint paint = new Paint();
		// helpers will only be drawn when debug is tagged on
		if (Options.DEBUG) {
			if (picture != null) {
				if (direction == UP) {
					canvas.drawBitmap(picture[UP], location.x, location.y,
							paint);
				}

				if (direction == DOWN) {
					canvas.drawBitmap(picture[DOWN], location.x, location.y,
							paint);
				}

				if (direction == LEFT) {
					canvas.drawBitmap(picture[LEFT], location.x, location.y,
							paint);
				}

				if (direction == RIGHT) {
					canvas.drawBitmap(picture[RIGHT], location.x, location.y,
							paint);
				}
			}
		}
	}

	public int getDirection() {
		return this.direction;
	}

	// loads all pictures
	public void loadBitmap(Bitmap[] bm) {
		this.picture = bm;
	}
}
