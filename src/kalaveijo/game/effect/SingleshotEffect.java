package kalaveijo.game.effect;

import kalaveijo.game.engine.Effect;
import kalaveijo.game.util.MapLocation;
import kalaveijo.game.util.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/*
 * Horrible horrible way of doing effects but oh well
 */
public class SingleshotEffect extends Effect {

	MapLocation endLocation;
	Point currentPoint;
	Point endPoint;
	int iX;
	int iY;
	int health;

	public SingleshotEffect(MapLocation startLocation, MapLocation endLocation,
			int health) {
		super(startLocation);
		this.health = health;
		this.endLocation = endLocation;
		if (startLocation != null)
			currentPoint = convertToPoint(startLocation);
		if (endLocation != null)
			endPoint = convertToPoint(endLocation);
		if (startLocation != null)
			calculateIncrements();

	}

	public void draw(Canvas c) {
		c.drawCircle(currentPoint.x, currentPoint.y, 5, new Paint());
		currentPoint.x = currentPoint.x + iX;
		currentPoint.y = currentPoint.y + iY;
	}

	public void causeDamage(int i) {
		// write special conditions how damage is caused
	}

	private Point convertToPoint(MapLocation ml) {
		return new Point(ml.x * Options.TILE_SIZE, ml.y * Options.TILE_SIZE);
	}

	// calculate how many steps can be taken
	private void calculateIncrements() {
		iX = (int) Math.floor((endPoint.x - currentPoint.x) / this.health);
		iY = (int) Math.floor((endPoint.y - currentPoint.y) / this.health);
	}

}
