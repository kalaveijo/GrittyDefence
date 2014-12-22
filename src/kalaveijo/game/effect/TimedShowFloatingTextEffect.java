package kalaveijo.game.effect;

import kalaveijo.game.engine.Entity;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.util.MapLocation;

public class TimedShowFloatingTextEffect extends TimedShowTextEffect {

	private int direction = Entity.UP;

	public TimedShowFloatingTextEffect(MapLocation startLocation,
			ObjectManager om, int howLong, String textToShow, int size,
			int direction) {
		super(startLocation, om, howLong, textToShow, size);
		// TODO Auto-generated constructor stub
	}

	public void move() {

		moveTowardsDirection();

		if (super.stopWatch.hasEnoughTimePassed()) {
			om.getRemoveList().add(this);
		}
	}

	private void moveTowardsDirection() {

		switch (direction) {

		case Entity.UP:
			this.location.y = this.location.y - 1;
			break;
		case Entity.UP_LEFT:
			this.location.y = this.location.y - 1;
			this.location.x = this.location.x - 1;
			break;
		case Entity.UP_RIGHT:
			this.location.y = this.location.y - 1;
			this.location.x = this.location.x + 1;
			break;
		case Entity.DOWN:
			this.location.y = this.location.y + 1;
			break;
		case Entity.DOWN_LEFT:
			this.location.y = this.location.y + 1;
			this.location.x = this.location.x - 1;
			break;
		case Entity.DOWN_RIGHT:
			this.location.y = this.location.y + 1;
			this.location.x = this.location.x + 1;
			break;
		case Entity.LEFT:
			this.location.x = this.location.x - 1;
			break;
		case Entity.RIGHT:
			this.location.x = this.location.x + 1;
			break;

		}

	}
}
