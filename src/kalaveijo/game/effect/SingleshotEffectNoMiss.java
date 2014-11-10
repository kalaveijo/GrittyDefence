package kalaveijo.game.effect;

import kalaveijo.game.engine.Entity;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.gameobjects.Unit;
import kalaveijo.game.util.MapLocation;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/*
 * Damage effect that does not miss
 */
public class SingleshotEffectNoMiss extends SingleshotEffect {

	Entity target;

	public SingleshotEffectNoMiss(MapLocation startLocation,
			MapLocation endLocation, int health, ObjectManager om, int damage) {
		super(startLocation, endLocation, health, om, damage);

		target = om.getEntityByPosition(endLocation);

	}

	public void draw(Canvas c) {

		Paint paint = new Paint();
		paint.setColor(Color.YELLOW);
		c.drawLine(currentPoint.x + ((float) Math.random() * 10),
				currentPoint.y + ((float) Math.random() * 10),
				target.getLocation().x + ((float) Math.random() * 10),
				target.getLocation().y + ((float) Math.random() * 10), paint);
	}

	public void causeDamage(int i) {
		// write special conditions how damage is caused
		if (target != null && target instanceof Unit) {
			Unit u = (Unit) target;
			int direction = u.calculateDirection(startLocation, endLocation);
			u.setLastHitDirection(direction);
			u.getDamage(damage);
		}

	}

}
