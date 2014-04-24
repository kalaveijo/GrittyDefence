package kalaveijo.game.engine;

import kalaveijo.game.util.MapLocation;
import android.graphics.Canvas;

/*
 * Handles data for some drawing effect
 */
public abstract class Effect {

	protected MapLocation startLocation;

	public Effect(MapLocation startLocation) {
		this.startLocation = startLocation;
	}

	public void draw(Canvas c) {

	}

	public void causeDamage(int i) {

	}

	// daasdasd
}
