package kalaveijo.game.effect;

import kalaveijo.game.engine.Effect;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.util.MapLocation;
import kalaveijo.game.util.Stopwatch;

/*
 * Base for effects that are tied to time
 */

abstract class TimedEffect extends Effect {

	protected Stopwatch stopWatch;
	
	public TimedEffect(MapLocation startLocation, ObjectManager om, int howLong) {
		super(startLocation, om);
		stopWatch = new Stopwatch(howLong);
		stopWatch.start();
	}

}
