package kalaveijo.game.grittydefence;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public interface Tickable {

	public void loadBitmap(Bitmap m);
	public void draw(Canvas c);
	public void move();
	public void remove();
	public void spawn();
	
}
