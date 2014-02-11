package kalaveijo.game.gameobjects;

import java.util.ArrayList;

import kalaveijo.game.engine.Entity;
import kalaveijo.game.engine.ObjectManager;

/*
 * Handles decision making by units
 */
public class Ai {

	protected int targetX, targetY; // movement target
	protected int range;
	protected int currentPosX, currentPosY;
	protected Unit u;

	public Ai(int currentPosX, int currentPosY, int range, Unit u) {
		this.currentPosX = currentPosX;
		this.currentPosY = currentPosY;
		this.targetX = currentPosX; // currently not used in anything
		this.targetY = currentPosY; // currently not used in anything
		this.range = range;
		this.u = u;
	} // Constructor

	public Ai(Unit u) {
		this.currentPosX = u.getPosX();
		this.currentPosY = u.getPosY();
		this.targetX = currentPosX; // currently not used in anything
		this.targetY = currentPosY; // currently not used in anything
		this.range = u.getRange();
		this.u = u;
	} // Constructor

	/*
	 * called by Unit move, checks what unit needs to do next and orders it
	 */
	public void assesAction() {

		// no point checking anything if unit is doing something already
		if (u.getStatus() == Entity.IDLE) {
			u.resetSpritePosition();
			this.currentPosX = u.getPosX();
			this.currentPosY = u.getPosY();
			// Check if enemies are nearby

			// Check where to move
			checkMovement();
		}
	}

	private void checkMovement() {

		int possibleTargetX = currentPosX;
		int possibleTargetY = currentPosY;
		int direction;

		// find correct helper
		ObjectManager om = u.getObjectManager();
		ArrayList<Map> map = om.getMap();
		MovementHelper[][] helpers = null;
		for (Map m : map) {
			helpers = m.getHelpers();
		}

		// check what direction helper points
		direction = helpers[currentPosX][currentPosY].getDirection();

		if (direction == MovementHelper.UP) {
			possibleTargetY = possibleTargetY - 1;
		}
		if (direction == MovementHelper.DOWN) {
			possibleTargetY = possibleTargetY + 1;
		}
		if (direction == MovementHelper.LEFT) {
			possibleTargetX = possibleTargetX - 1;
		}
		if (direction == MovementHelper.RIGHT) {
			possibleTargetX = possibleTargetX + 1;
		}

		// Check if next tile is free
		if (om.mapLocationIsFree(possibleTargetX, possibleTargetY)) {
			if (om.noneIsMovingToMapLocation(possibleTargetX, possibleTargetY)) {
				// if so, point target for unit and change status to moving
				u.setNextTileX(possibleTargetX);
				u.setNextTileY(possibleTargetY);
				u.setStatus(Entity.MOVING);
			}
		}
	}
}
