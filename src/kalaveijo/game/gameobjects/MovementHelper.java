package kalaveijo.game.gameobjects;

import kalaveijo.game.util.Options;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

/*
 * Class under map that tells enemy units where they can move next
 * Each helper only points into certain direction
 */
public class MovementHelper extends Unit implements Tickable {

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

		// helpers will only be drawn when debug is tagged on
		if (Options.DEBUG) {
			if (picture != null) {
				if (direction == UP) {
					// canvas.drawBitmap(picture[UP]);
				}

				if (direction == DOWN) {

				}

				if (direction == LEFT) {

				}

				if (direction == RIGHT) {

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
