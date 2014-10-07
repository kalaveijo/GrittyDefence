package kalaveijo.game.engine;

import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.util.MapLocation;
import android.graphics.Canvas;

/*
 * Handles data for some drawing effect
 */
public abstract class Effect extends Entity {

	protected MapLocation startLocation;

	public Effect(MapLocation startLocation, ObjectManager om) {
		this.startLocation = startLocation;
		this.om = om;
	}

	public void draw(Canvas c) {

	}

	public void causeDamage(int i) {

	}

	// daasdasd
}
