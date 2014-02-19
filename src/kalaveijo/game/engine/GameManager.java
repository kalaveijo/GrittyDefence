package kalaveijo.game.engine;

import kalaveijo.game.gameobjects.Mission;

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

		// add objects to object manager

	}

	private void spawnWave(int waveNumber) {

	}

	private void spawnWave(String waveName) {

	}

}
