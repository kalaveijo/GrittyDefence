package kalaveijo.game.engine.manager;

import java.util.ArrayList;

import kalaveijo.game.gameobjects.Map;
import kalaveijo.game.gameobjects.Mission;
import kalaveijo.game.gameobjects.MissionWave;
import kalaveijo.game.gameobjects.SpawnTile;
import kalaveijo.game.gameobjects.Unit;

/*
 * Handles game progression, kinda works like dungeon master
 */
public class GameManager {

	private TemplateManager templateManager;
	private ObjectManager objectManager;
	private Mission currentMission;
	private boolean isBuildPhase = false;
	private int waveNumber = 1;

	public GameManager(ObjectManager om, TemplateManager tm) {
		this.templateManager = tm;
		this.objectManager = om;
	}

	// asses current game situation
	// handles wave changes, mission changes etc.
	// called in GameThread.tick();
	public void assesGameSituation() {

		// check if there are still enemy units on game area

		// check if there are waves left in mission

	}

	public void changeMission(String missionName) {
		this.currentMission = templateManager.createMission(missionName);
		// instantiate new objects from templates

		// remove previous objects from object manager
		objectManager.getEnemyUnits().clear();
		objectManager.getPlayerUnits().clear();
		objectManager.getMap().clear();
		objectManager.getMapUnits().clear();

		// add objects to object manager
		objectManager.getMap().add(currentMission.getMap());
		spawnWave(waveNumber);

	}

	private void spawnWave(int waveNumber) {
		Map map = currentMission.getMap();
		ArrayList<SpawnTile> spawns = map.getSpawners();
		int i = 0;
		// find correct wave
		for (MissionWave wave : currentMission.getWaveList()) {
			if (wave.getWaveNumber() == waveNumber) {
				// Spawn units
				for (Unit u : wave.getEnemyUnitList()) {
					// find spawn points and place unit there
					if (i < spawns.size()) {
						objectManager.spawnEnemyUnit(u,
								spawns.get(i).getPosX(), spawns.get(i)
										.getPosY());
						i++;
					}
				}
			}
		}

	}

	private void spawnWave(String waveName) {

	}

}
