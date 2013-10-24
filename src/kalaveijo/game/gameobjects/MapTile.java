package kalaveijo.game.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class MapTile implements Tickable {

	private Point location;
	private int tileType; // type of terrain
	private Bitmap picture;

	public MapTile(Point location, int tileType) {
		this.location = location;
		this.tileType = tileType;
	}

	@Override
	public void draw(Canvas c, Paint mPaint) {
		c.drawBitmap(picture, location.x, location.y, mPaint);
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
	public void spawn() {
		// TODO Auto-generated method stub

	}

	// should load bitmap related to type of terrain, implement later
	public void loadBitmap(Bitmap m) {
		this.picture = m;
	}

}
