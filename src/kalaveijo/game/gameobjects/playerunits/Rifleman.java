package kalaveijo.game.gameobjects.playerunits;

import kalaveijo.game.gameobjects.Tickable;
import kalaveijo.game.gameobjects.Unit;
import kalaveijo.game.util.Options;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Rifleman extends Unit implements Tickable {

	private int health = 10;

	public Rifleman(long id) {
		super(id);
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

			// Skipped before implementation
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
	}

}
