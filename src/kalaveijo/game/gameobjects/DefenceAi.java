package kalaveijo.game.gameobjects;

import java.util.ArrayList;

import kalaveijo.game.engine.Entity;
import kalaveijo.game.util.MapLocation;

public class DefenceAi extends Ai {

	public DefenceAi(int currentPosX, int currentPosY, int range, Unit u) {
		super(currentPosX, currentPosY, range, u);
		// TODO Auto-generated constructor stub
	}

	public DefenceAi(Unit u) {
		super(u);
	}

	protected void checkMovement() {
		// leave empty, this motherfucker aint moving see?
	}

	protected void checkAttack() {
		boolean hasAttacked = false;
		ArrayList<MapLocation> targetList = getTilesOnRange(u.getPosX(),
				u.getPosY(), u.getRange(), u.getObjectManager());
		// go throught targets in range
		/*
		 * paska implementaatio, pit�isi duunaa ett� se ei katso joka vitun
		 * entity� pelist� vaan oikeasti tutkisi mit� siell� alueella on
		 */
		for (MapLocation ml : targetList) {
			for (Entity e : u.getObjectManager().getEnemyUnits()) {
				if (e.getPosX() == ml.x && e.getPosY() == ml.y) {
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
