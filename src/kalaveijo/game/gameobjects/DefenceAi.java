package kalaveijo.game.gameobjects;

import java.util.ArrayList;

import kalaveijo.game.engine.Entity;
import kalaveijo.game.util.MapLocation;

public class DefenceAi extends Ai {

	MapLocation targetLocation;

	public DefenceAi(int currentPosX, int currentPosY, int range, Unit u) {
		super(currentPosX, currentPosY, range, u);
	}

	public DefenceAi(Unit u) {
		super(u);
	}

	public void assesAction() {

		// if not attacking
		if (u.getStatus() == Entity.IDLE) {
			u.resetSpritePosition();
			this.currentPosX = u.getPosX();
			this.currentPosY = u.getPosY();

			// Check where to move
			checkMovement();
		}

		// no point checking anything if unit is doing something already
		if (u.getStatus() == Entity.IDLE) {
			u.resetSpritePosition();
			this.currentPosX = u.getPosX();
			this.currentPosY = u.getPosY();
			// Check if enemies are nearby
			checkAttack();
		}
	}

	protected void checkMovement() {
		if (targetLocation != null) {
			int possibleTargetX = currentPosX;
			int possibleTargetY = currentPosY;
			boolean hasChanged = false;

			// check if should move up
			if (u.getPosY() > targetLocation.y) {
				possibleTargetY = possibleTargetY - 1;
				hasChanged = true;
			}

			// check if should move down
			if (u.getPosY() < targetLocation.y) {
				possibleTargetY = possibleTargetY + 1;
				hasChanged = true;
			}

			// check if should move right
			if (u.getPosX() < targetLocation.x) {
				possibleTargetX = possibleTargetX + 1;
				hasChanged = true;
			}

			// check if should move left
			if (u.getPosX() > targetLocation.x) {
				possibleTargetX = possibleTargetX - 1;
				hasChanged = true;
			}

			// Check if next tile is free
			if (hasChanged) {
				if (u.getObjectManager().mapLocationIsFree(possibleTargetX,
						possibleTargetY)) {
					if (u.getObjectManager().noneIsMovingToMapLocation(
							possibleTargetX, possibleTargetY)) {
						// also check if map location is passable
						if (u.getObjectManager()
								.getMap()
								.get(0)
								.getTile(
										new MapLocation(possibleTargetX,
												possibleTargetY)).getTileType() != 1) {
							u.orderMove(possibleTargetX, possibleTargetY);
						}
					}
				}
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
			for (Entity e : u.getObjectManager().getEnemyUnits()) {
				if (e.getPosX() == ml.x && e.getPosY() == ml.y) {
					if (e.getStatus() != e.DYING) {
						if (!hasAttacked) {
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

	public void setTargetLocation(MapLocation newTargetLocation) {
		this.targetLocation = newTargetLocation;
	}
}
