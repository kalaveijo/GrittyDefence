package kalaveijo.game.gameobjects;

import kalaveijo.game.util.Options;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/*
 * Basic superclass for game unit
 */
public class Unit implements Tickable {

	protected int offSetX = Options.TILE_SIZE / 2,
			offSetY = Options.TILE_SIZE / 2;
	protected int Health = 0;
	protected long id;
	protected Ai ai = null;
	protected Point location = null;
	protected int posX, posY = -1;

	public Unit(long id) {
		this.id = id;
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
	public void draw(Canvas c, Paint mPaint) {
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

}
