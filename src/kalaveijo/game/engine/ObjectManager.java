package kalaveijo.game.engine;

import java.util.ArrayList;

import kalaveijo.game.gameobjects.Map;
import kalaveijo.game.util.Options;
import android.graphics.Point;

/*
 * Handles all and any game objects
 */
public class ObjectManager {

	private long idCount = 0;
	private ArrayList<Entity> entityTemplates = new ArrayList<Entity>();
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

	public void addEntityTemplate(Entity entity) {
		entityTemplates.add(entity);
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

		Point point = new Point(x * Options.TILE_SIZE, y * Options.TILE_SIZE);
		playerUnit.add(u);
		u.spawn(point, x, y);
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

	// used to check if someone else is trying to go to desired tile
	public boolean noneIsMovingToMapLocation(int x, int y) {

		for (Entity u : enemyUnit) {
			if (u.getNextTileX() == x && u.getNextTileY() == y) {
				return false;
			}// if
		}// for

		for (Entity u : playerUnit) {
			if (u.getNextTileX() == x && u.getNextTileY() == y) {
				return false;
			}// if
		}// for

		return true;
	}

	public long getNextFreeId() {
		long i = this.idCount;
		this.idCount++;
		return i;
	}// getNextFreeId

	public void addToMapUnits(ArrayList al) {
		this.mapUnits.add(al);
	}

	// run single game tick for all Entities
	public void tick() {
		for (Entity u : getPlayerUnits()) {
			u.move();
		}
		for (Entity u : getEnemyUnits()) {
			u.move();
		}
	}

}// class
