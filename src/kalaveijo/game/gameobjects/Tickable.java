package kalaveijo.game.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public interface Tickable {

	public void loadBitmap(Bitmap m);

	public void draw(Canvas c, Paint mPaint);

	public void move();

	public void remove();

	public void spawn(Point location, int x, int y);

}
