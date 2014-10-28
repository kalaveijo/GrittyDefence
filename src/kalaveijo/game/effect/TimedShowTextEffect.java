package kalaveijo.game.effect;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.util.MapLocation;
import kalaveijo.game.util.Options;

public class TimedShowTextEffect extends TimedEffect {

	private String text;
	
	public TimedShowTextEffect(MapLocation startLocation, ObjectManager om,
			int howLong, String textToShow) {
		super(startLocation, om, howLong);
		this.text = textToShow;
	}
	
	public void move(){
		if(super.stopWatch.hasEnoughTimePassed()){
			om.getRemoveList().add(this);
		}
	}
	
	public void draw(Canvas c){
		Point point = new Point(startLocation.x*Options.TILE_SIZE, startLocation.y*Options.TILE_SIZE);
		c.drawText(text, point.x, point.y, new Paint());
	}

}
