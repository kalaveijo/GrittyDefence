package kalaveijo.game.gameobjects;

import java.util.ArrayList;

import kalaveijo.game.engine.Entity;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.util.MapLocation;

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
			checkAttack();
		}
		// if not attacking
		if (u.getStatus() == Entity.IDLE) {
			u.resetSpritePosition();
			this.currentPosX = u.getPosX();
			this.currentPosY = u.getPosY();

			// Check where to move
			checkMovement();
		}
	}

	protected void checkMovement() {

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
		if (direction == MovementHelper.UPLEFT) {

			if (Math.random() < 0.5) {
				// go up
				possibleTargetY = possibleTargetY - 1;
			} else {
				// go left
				possibleTargetX = possibleTargetX - 1;
			}

		}
		if (direction == MovementHelper.DOWNLEFT) {
			if (Math.random() < 0.5) {
				// go down
				possibleTargetY = possibleTargetY + 1;
			} else {
				// go left
				possibleTargetX = possibleTargetX - 1;
			}
		}
		if (direction == MovementHelper.UPDOWNLEFT) {
			Double d = Math.random();
			if (d < 0.3) {
				// go up
				possibleTargetY = possibleTargetY - 1;
			} else if (d < 0.6) {
				// go left
				possibleTargetX = possibleTargetX - 1;
			} else {
				// go down
				possibleTargetY = possibleTargetY + 1;
			}
		}

		// Check if next tile is free
		if (om.mapLocationIsFree(possibleTargetX, possibleTargetY)) {
			if (om.noneIsMovingToMapLocation(possibleTargetX, possibleTargetY)) {
				u.orderMove(possibleTargetX, possibleTargetY);
			}
		}
	}

	protected void checkAttack() {
		boolean hasAttacked = false;
		ArrayList<MapLocation> targetList = getTilesOnRange(u.getPosX(),
				u.getPosY(), u.getRange(), u.getObjectManager());
		// go throught targets in range
		/*
		 * paska implementaatio, pitäisi duunaa että se ei katso joka vitun
		 * entityä pelistä vaan oikeasti tutkisi mitä siellä alueella on
		 */
		for (MapLocation ml : targetList) {
			for (Entity e : u.getObjectManager().getPlayerUnits()) {
				if (e.getPosX() == ml.x && e.getPosY() == ml.y) {
					if (!hasAttacked) {
						if (e instanceof Mine == false) {
							// fire projectile at target
							u.attack(new MapLocation(e.getPosX(), e.getPosY()));

							/*
							 * Range needs to be changed to come from the unit
							 * (prolly not stored atm)
							 */
							u.setStatus(Entity.ATTACKING);
							hasAttacked = true;
						}
					}
				}
			}

		}

	}

	// see getTilesOnRangeAlgorithm.png from project_files
	public ArrayList<MapLocation> getTilesOnRange(int startX, int startY,
			int range, ObjectManager om) {
		Map map = null;
		for (Map m : om.getMap()) {
			map = m;
		}
		int currentX = startX;
		int currentY = startY;
		ArrayList<MapLocation> selectedTiles = new ArrayList<MapLocation>();

		if (range == 0) {
			selectedTiles.add(new MapLocation(startX, startY));
			return selectedTiles;
		}

		// miten monta kierrosta halutaan
		for (int i = 1; i < range + 1; i++) {

			if (i < 3) {
				currentX = currentX - i;
			} else {
				currentX = currentX - (i - 1);
			}
			selectedTiles.add(new MapLocation(currentX, currentY));
			for (int e = 0; e < i; e++) {
				currentX = currentX + 1;
				currentY = currentY - 1;

				if (currentX > -1 && currentY > -1 && currentX < map.getSizeX()
						&& currentY < map.getSizeY())
					selectedTiles.add(new MapLocation(currentX, currentY));
			}
			for (int e = 0; e < i; e++) {
				currentX = currentX + 1;
				currentY = currentY + 1;

				if (currentX > -1 && currentY > -1 && currentX < map.getSizeX()
						&& currentY < map.getSizeY())
					selectedTiles.add(new MapLocation(currentX, currentY));
			}
			for (int e = 0; e < i; e++) {
				currentX = currentX - 1;
				currentY = currentY + 1;

				if (currentX > -1 && currentY > -1 && currentX < map.getSizeX()
						&& currentY < map.getSizeY())
					selectedTiles.add(new MapLocation(currentX, currentY));
			}

			for (int e = 0; e < i - 1; e++) {
				currentX = currentX - 1;
				currentY = currentY - 1;
				if (currentX > -1 && currentY > -1 && currentX < map.getSizeX()
						&& currentY < map.getSizeY())
					selectedTiles.add(new MapLocation(currentX, currentY));
			}

			currentY = currentY - 1;

		}
		return selectedTiles;
	}
}
