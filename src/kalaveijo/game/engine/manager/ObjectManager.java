package kalaveijo.game.engine.manager;

import java.util.ArrayList;

import kalaveijo.game.engine.Entity;
import kalaveijo.game.gameobjects.Map;
import kalaveijo.game.gameobjects.Mission;
import kalaveijo.game.util.Options;
import android.graphics.Point;

/*
 * Handles all and any game objects
 */
public class ObjectManager {

	private long idCount = 0;
	private ArrayList<Entity> entityTemplates = new ArrayList<Entity>();

	private TemplateManager tm;

	private ArrayList<Entity> playerUnit = new ArrayList<Entity>();
	private ArrayList<Entity> enemyUnit = new ArrayList<Entity>();
	private ArrayList<Entity> mapUnits = new ArrayList<Entity>();
	private ArrayList<Mission> missions = new ArrayList<Mission>();
	private ArrayList<Entity> liveProjectiles = new ArrayList<Entity>();
	private ArrayList<Map> map = new ArrayList<Map>();

	public ObjectManager() {
		// initialize map
		// map.add(new Map(getNextFreeId(), this, 21, 11));
	}// Constructor

	public void addMap(Map map) {
		if (map != null) {
			this.map.add(map);
		} else {
			this.map.add(new Map(getNextFreeId(), this, 21, 11));
		}
	}

	public ArrayList<Entity> getMapUnits() {
		return mapUnits;
	}

	public void addEntityTemplate(Entity entity) {
		entityTemplates.add(entity);
	}

	public void setMapUnits(ArrayList<Entity> mapUnits) {
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

	public boolean spawnEnemyUnit(Entity u, int x, int y) {

		// if place is occupied, fail
		if (!mapLocationIsFree(x, y)) {
			return false;
		}

		Point point = new Point(x * Options.TILE_SIZE, y * Options.TILE_SIZE);
		enemyUnit.add(u);
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

	public void addToMapUnits(Entity al) {
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

	public ArrayList<Mission> getMissions() {
		return missions;
	}

	public void addToMissions(Mission missions) {
		this.missions.add(missions);
	}

	public TemplateManager getTemplateManager() {
		return tm;
	}

	public void setTemplateManager(TemplateManager tm) {
		this.tm = tm;
	}

	public ArrayList<Entity> getLiveProjectiles() {
		return liveProjectiles;
	}

}// class
