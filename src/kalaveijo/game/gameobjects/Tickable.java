package kalaveijo.game.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public interface Tickable {

	public void loadBitmap(Bitmap m);

	public void draw(Canvas c, Paint mPaint);

	public void move();

	public void remove();

	public void spawn();

}
