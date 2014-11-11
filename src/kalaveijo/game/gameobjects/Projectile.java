package kalaveijo.game.gameobjects;

import kalaveijo.game.effect.SingleshotEffect;
import kalaveijo.game.effect.SingleshotEffectNoMiss;
import kalaveijo.game.effect.SingleshotEffectNoMissExplosion;
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
	private int damage;

	public Projectile(long id, ObjectManager om, MapLocation targetLocation,
			MapLocation currentLocation, int health, String effect, int damage) {
		super(id, om);
		this.targetLocation = targetLocation;
		this.currentLocation = currentLocation;
		this.health = health;
		super.status = Entity.MOVING;
		this.damage = damage;
		this.parsedEffect = convertEffect(effect);
	}

	public void move() {
		if (health == 0) {
			causeDamage();
			parsedEffect = null;
			om.getRemoveList().add(this);
		} else {
			health--;
		}
	}

	public void draw(Canvas c) {
		if (parsedEffect != null)
			parsedEffect.draw(c);
	}

	private void causeDamage() {
		if (parsedEffect != null)
			parsedEffect.causeDamage(damage);
	}

	private Effect convertEffect(String effect) {

		if (effect.equals("singleshot")) {
			return new SingleshotEffect(currentLocation, targetLocation,
					super.health, om, damage);
		}

		if (effect.equals("singleshotnomiss")) {
			return new SingleshotEffectNoMiss(currentLocation, targetLocation,
					super.health, om, damage);
		}

		if (effect.equals("singleshotnomissexplosion")) {
			return new SingleshotEffectNoMissExplosion(currentLocation,
					targetLocation, super.health, om, damage);
		}

		// if all fails, default singleshot
		return new SingleshotEffect(currentLocation, targetLocation,
				super.health, om, damage);
	}

	public int getDamage() {
		return this.damage;
	}
}
