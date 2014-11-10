package kalaveijo.game.effect;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.util.MapLocation;
import kalaveijo.game.util.Options;

/*
 * Shows text next to Maplocation for X milliseconds
 */
public class TimedShowTextEffect extends TimedEffect {

	private String text;
	private int size = 12;
	
	public TimedShowTextEffect(MapLocation startLocation, ObjectManager om,
			int howLong, String textToShow, int size) {
		super(startLocation, om, howLong);
		this.text = textToShow;
		this.size = size;
	}
	
	public void move(){
		if(super.stopWatch.hasEnoughTimePassed()){
			om.getRemoveList().add(this);
		}
	}
	
	public void draw(Canvas c){
		Point point = new Point(startLocation.x*Options.TILE_SIZE, startLocation.y*Options.TILE_SIZE);
		Paint paint = new Paint();
		paint.setTextSize(size);
		c.drawText(text, point.x, point.y, paint);
	}

}
