package kalaveijo.game.util;

import android.graphics.Bitmap;
import android.graphics.Point;

/*
 * Used to wrap bitmaps with intreguusting metadata
 */
public class PositionedBitmapWrapper {

	Bitmap picture;
	Point location;
	MapLocation ml;

	public PositionedBitmapWrapper(Bitmap picture, Point location,
			MapLocation ml) {
		this.picture = picture;
		this.location = location;
		this.ml = ml;
	}

	public Bitmap getPicture() {
		return picture;
	}

	public Point getLocation() {
		return location;
	}

	public MapLocation getMl() {
		return ml;
	}

}
