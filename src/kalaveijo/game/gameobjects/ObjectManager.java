package kalaveijo.game.gameobjects;

import java.util.ArrayList;

/*
 * Handles all and any game objects
 */
public class ObjectManager {

	private long idCount = 0;
	private ArrayList<Unit> playerUnit = new ArrayList<Unit>();
	private ArrayList<Unit> enemyUnit = new ArrayList<Unit>();
	private ArrayList<ArrayList> mapUnits = new ArrayList<ArrayList>();
	private ArrayList<Map> map = new ArrayList<Map>();

	public ObjectManager() {
		// initialize map
		map.add(new Map(getNextFreeId(), this, 21, 11));
	}// Constructor

	public ArrayList<ArrayList> getMapUnits() {
		return mapUnits;
	}

	public void setMapUnits(ArrayList<ArrayList> mapUnits) {
		this.mapUnits = mapUnits;
	}

	public ArrayList<Map> getMap() {
		return this.map;
	}// getMap()

	public ArrayList<Unit> getPlayerUnits() {
		return this.playerUnit;
	}// getPlayerUnits()

	public ArrayList<Unit> getEnemyUnits() {
		return this.enemyUnit;
	}// getEnemyUnits

	public boolean spawnPlayerUnit(Unit u, int x, int y) {

		// if place is occupied, fail
		if (!mapLocationIsFree(x, y)) {
			return false;
		}

		MapTile[][] mt = new MapTile[0][0];
		for (Map ma : map) { // Assuming we only have one map
			mt = ma.getTiles();
		}

		playerUnit.add(u);
		u.spawn(mt[x][y].getLocation(), x, y);
		return true;
	}// spawnPlayerUnit

	// checks if give tile in x,y is free, returns true if tile is free
	public boolean mapLocationIsFree(int x, int y) {
		for (Unit u : enemyUnit) {
			if (u.getPosX() == x && u.getPosY() == y) {
				return false;
			}// if
		}// for

		for (Unit u : playerUnit) {
			if (u.getPosX() == x && u.getPosY() == y) {
				return false;
			}// if
		}// for

		return true;
	}// mapLocationisFree

	public long getNextFreeId() {
		long i = this.idCount;
		this.idCount++;
		return i;
	}// getNextFreeId

	public void addToMapUnits(ArrayList al) {
		this.mapUnits.add(al);
	}

}// class
