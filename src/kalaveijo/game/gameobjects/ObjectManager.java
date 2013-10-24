package kalaveijo.game.gameobjects;

import java.util.ArrayList;

/*
 * Handles all and any game objects
 */
public class ObjectManager {

	private ArrayList<Unit> playerUnit = new ArrayList<Unit>();
	private ArrayList<Unit> enemyUnit = new ArrayList<Unit>();
	private ArrayList<Map> map = new ArrayList<Map>();

	public ObjectManager() {
		// initialize map
		map.add(new Map());
	}// Constructor

	public ArrayList<Map> getMap() {
		return this.map;
	}// getMap()

	public ArrayList<Unit> getPlayerUnits() {
		return this.playerUnit;
	}// getPlayerUnits()

	public ArrayList<Unit> getEnemyUnits() {
		return this.enemyUnit;
	}// getEnemyUnits

}// class
