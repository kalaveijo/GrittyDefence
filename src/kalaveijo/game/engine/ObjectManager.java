package kalaveijo.game.engine;

import java.util.ArrayList;

import kalaveijo.game.gameobjects.Map;
import kalaveijo.game.gameobjects.MapTile;

/*
 * Handles all and any game objects
 */
public class ObjectManager {

	private long idCount = 0;
	private ArrayList<Entity> playerUnit = new ArrayList<Entity>();
	private ArrayList<Entity> enemyUnit = new ArrayList<Entity>();
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

	public ArrayList<Entity> getPlayerUnits() {
		return this.playerUnit;
	}// getPlayerUnits()

	public ArrayList<Entity> getEnemyUnits() {
		return this.enemyUnit;
	}// getEnemyUnits

	public boolean spawnPlayerUnit(Entity u, int x, int y) {

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
		for (Entity u : enemyUnit) {
			if (u.getPosX() == x && u.getPosY() == y) {
				return false;
			}// if
		}// for

		for (Entity u : playerUnit) {
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
