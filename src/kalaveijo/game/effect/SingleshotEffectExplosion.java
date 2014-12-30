package kalaveijo.game.effect;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import kalaveijo.game.engine.Entity;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.gameobjects.Projectile;
import kalaveijo.game.gameobjects.Unit;
import kalaveijo.game.util.MapLocation;
import kalaveijo.game.util.Options;

public class SingleshotEffectExplosion extends SingleshotEffect {

	private int offSet = 5;
	private int radius = 1;
	private Projectile projectile;
	private boolean runOnce = true;
	private int scatterDamage;

	public SingleshotEffectExplosion(MapLocation startLocation,
			MapLocation endLocation, int health, ObjectManager om, int damage,
			Projectile projectile) {
		super(startLocation, endLocation, health, om, damage);
		this.projectile = projectile;
		scatterDamage = (int) Math.ceil((damage * 0.64));
	}

	public void draw(Canvas c) {
		if (projectile.getHealth() > offSet) {
			c.drawCircle(currentPoint.x, currentPoint.y, 5, new Paint());
			currentPoint.x = currentPoint.x + iX;
			currentPoint.y = currentPoint.y + iY;
		} else {
			c.drawCircle(currentPoint.x, currentPoint.y, 5, new Paint());
			currentPoint.x = currentPoint.x + iX;
			currentPoint.y = currentPoint.y + iY;
			Paint paint = new Paint();
			paint.setColor(Color.YELLOW);
			c.drawCircle(endPoint.x, endPoint.y, radius, paint);
			c.drawCircle(endPoint.x - Options.TILE_SIZE, endPoint.y, radius,
					paint); // left
			c.drawCircle(endPoint.x, endPoint.y - Options.TILE_SIZE, radius,
					paint); // above
			c.drawCircle(endPoint.x + Options.TILE_SIZE, endPoint.y, radius,
					paint); // right
			c.drawCircle(endPoint.x, endPoint.y + Options.TILE_SIZE, radius,
					paint); // down
			radius = radius + 5;
		}
	}

	public void causeDamage(int i) {
		// write special conditions how damage is caused

		ArrayList<Entity> unitsGettingHit = new ArrayList<Entity>();
		unitsGettingHit.add(om.getEntityByPosition(new MapLocation(
				endLocation.x - 1, endLocation.y))); // left
		unitsGettingHit.add(om.getEntityByPosition(new MapLocation(
				endLocation.x, endLocation.y - 1))); // above
		unitsGettingHit.add(om.getEntityByPosition(new MapLocation(
				endLocation.x + 1, endLocation.y))); // right
		unitsGettingHit.add(om.getEntityByPosition(new MapLocation(
				endLocation.x, endLocation.y + 1))); // down

		Entity d = om.getEntityByPosition(endLocation);
		
		if (d != null && d instanceof Unit) {
			if (!om.getPlayerUnits().contains(d)) {
				Unit u = (Unit) d;
				int direction = u.calculateDirection(startLocation,
						endLocation);
				u.setLastHitDirection(direction);
				u.getDamage(damage);

			}
		}
		
		for (Entity e : unitsGettingHit) {
			if (e != null && e instanceof Unit) {
				if (!om.getPlayerUnits().contains(e)) {
					Unit u = (Unit) e;
					int direction = u.calculateDirection(startLocation,
							endLocation);
					u.setLastHitDirection(direction);
					u.getDamage(scatterDamage);

				}
			}
		}
	}

}
