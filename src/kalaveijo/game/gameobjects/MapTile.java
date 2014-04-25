package kalaveijo.game.gameobjects;

import kalaveijo.game.engine.Entity;
import kalaveijo.game.engine.Tickable;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.util.Options;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class MapTile extends Entity implements Tickable {

	private Point location;
	private int tileType; // type of terrain
	private Bitmap picture; // not in use, check BitmapContainerGroup

	public MapTile(long id, ObjectManager om, Point location, int tileType) {
		super(id, om);
		this.location = location;
		this.tileType = tileType;
	}

	@Override
	public void draw(Canvas c) {

		if (picture != null) {
			c.drawBitmap(picture, location.x, location.y, new Paint());
		} else {

			Rect r = new Rect(location.x, location.y, location.x
					+ Options.TILE_SIZE, location.y + Options.TILE_SIZE);
			Paint p = new Paint();
			p.setColor(Color.GRAY);
			p.setStyle(Paint.Style.STROKE);
			p.setStrokeWidth(3);
			c.drawRect(r, p);

		}
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

	public int getTileType() {
		return this.tileType;
	}

	public Point getLocation() {
		return this.location;
	}

	// should load bitmap related to type of terrain, implement later
	public void loadBitmap(Bitmap m) {
		this.picture = m;
	}

	public Bitmap getBitmap() {
		return this.picture;
	}

}
