package kalaveijo.game.effect;

import java.util.ArrayList;

import kalaveijo.game.engine.Entity;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.gameobjects.Mine;
import kalaveijo.game.gameobjects.Unit;
import kalaveijo.game.util.MapLocation;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class SingleshotEffectNoMissExplosion extends SingleshotEffectNoMiss {

	private int radius = 1;

	public SingleshotEffectNoMissExplosion(MapLocation startLocation,
			MapLocation endLocation, int health, ObjectManager om, int damage) {
		super(startLocation, endLocation, health, om, damage);
		// TODO Auto-generated constructor stub
	}

	public void draw(Canvas c) {

		Paint paint = new Paint();
		paint.setColor(Color.YELLOW);
		c.drawCircle(currentPoint.x, currentPoint.y, radius, paint);
		radius = radius + 3;
	}

	public void causeDamage(int i) {

		// write special conditions how damage is caused

		for (Unit target : getAllTargets()) {
			if (target != null && target instanceof Unit) {
				Unit u = (Unit) target;
				int direction = u
						.calculateDirection(startLocation, endLocation);
				u.setLastHitDirection(direction);
				if (u instanceof Mine) {
					u.getDamage(1);
				} else {
					u.getDamage(damage);
				}

			}
		}

	}

	private ArrayList<Unit> getAllTargets() {

		ArrayList<Unit> list = new ArrayList<Unit>();

		for (Entity e : om.getEnemyUnits()) {
			if (e.getPosX() == startLocation.x
					&& e.getPosY() == startLocation.y) {
				list.add((Unit) e);
			}
		}

		for (Entity e : om.getPlayerUnits()) {
			if (e.getPosX() == startLocation.x
					&& e.getPosY() == startLocation.y) {
				list.add((Unit) e);
			}
		}

		return list;
	}
}
