package kalaveijo.game.grittydefence;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class MapTile implements Tickable {

	private Point location;
	private int tileType; // type of terrain
	private Bitmap picture;
	private Canvas c;
	private Paint mPaint;
	
	public MapTile(Point location, int tileType, Canvas c, Paint mPaint){
		this.location = location;
		this.tileType = tileType;
		this.c = c;
		this.mPaint = mPaint;
	}
	
	@Override
	public void draw(Canvas c) {
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

	//should load bitmap related to type of terrain, implement later
	public void loadBitmap(Bitmap m) {
		this.picture = m;
	}


}
