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
			this.posY = this.posY - 1;
			break;
		case Entity.UP_LEFT:
			this.posY = this.posY - 1;
			this.posX = this.posX - 1;
			break;
		case Entity.UP_RIGHT:
			this.posY = this.posY - 1;
			this.posX = this.posX + 1;
			break;
		case Entity.DOWN:
			this.posY = this.posY + 1;
			break;
		case Entity.DOWN_LEFT:
			this.posY = this.posY + 1;
			this.posX = this.posX - 1;
			break;
		case Entity.DOWN_RIGHT:
			this.posY = this.posY + 1;
			this.posX = this.posX + 1;
			break;
		case Entity.LEFT:
			this.posX = this.posX - 1;
			break;
		case Entity.RIGHT:
			this.posX = this.posX + 1;
			break;

		}

	}
}
