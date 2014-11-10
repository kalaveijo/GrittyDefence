package kalaveijo.game.effect;

import kalaveijo.game.engine.Effect;
import kalaveijo.game.engine.Entity;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.gameobjects.Unit;
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
	ObjectManager om;
	int damage;

	public SingleshotEffect(MapLocation startLocation, MapLocation endLocation,
			int health, ObjectManager om, int damage) {
		super(startLocation, om);
		this.om = om;
		this.health = health;
		this.endLocation = endLocation;
		this.damage = damage;
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
		Entity e = om.getEntityByPosition(endLocation);
		if (e != null && e instanceof Unit) {
			Unit u = (Unit) e;
			int direction = u.calculateDirection(startLocation, endLocation);
			u.setLastHitDirection(direction);
			u.getDamage(damage);
		}
	}

	private Point convertToPoint(MapLocation ml) {
		return new Point(ml.x * Options.TILE_SIZE + (Options.TILE_SIZE / 2),
				ml.y * Options.TILE_SIZE + (Options.TILE_SIZE / 2));
	}

	// calculate how many steps can be taken
	private void calculateIncrements() {
		iX = (int) Math.floor((endPoint.x - currentPoint.x) / this.health);
		iY = (int) Math.floor((endPoint.y - currentPoint.y) / this.health);
	}

}
