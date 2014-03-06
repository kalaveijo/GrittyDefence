package kalaveijo.game.gameobjects;

import kalaveijo.game.effect.SingleshotEffect;
import kalaveijo.game.engine.Effect;
import kalaveijo.game.engine.Entity;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.util.MapLocation;
import android.graphics.Canvas;

/*
 * Handles single projectile object
 */
public class Projectile extends Entity {

	private MapLocation targetLocation;
	private MapLocation currentLocation;
	protected String effect;
	private Effect parsedEffect;

	public Projectile(long id, ObjectManager om, MapLocation targetLocation,
			MapLocation currentLocation, int health, String effect, int damage) {
		super(id, om);
		this.targetLocation = targetLocation;
		this.currentLocation = currentLocation;
		this.health = health;
		super.status = Entity.MOVING;
		this.parsedEffect = convertEffect(effect);
	}

	public void move() {
		if (health == 0) {
			causeDamage();
			om.getLiveProjectiles().remove(this);
		} else {
			health--;
		}
	}

	public void draw(Canvas c) {
		parsedEffect.draw(c);
	}

	private void causeDamage() {
		// cause damage according to effect
	}

	private Effect convertEffect(String effect) {

		if (effect.equals("singleshot")) {
			return new SingleshotEffect(null);
		}

		// if all fails, default singleshot
		return new SingleshotEffect(null);
	}

}
