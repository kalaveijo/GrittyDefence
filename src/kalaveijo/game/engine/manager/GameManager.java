package kalaveijo.game.engine.manager;

import java.util.ArrayList;

import kalaveijo.game.effect.TimedShowTextEffect;
import kalaveijo.game.engine.Entity;
import kalaveijo.game.gameobjects.Map;
import kalaveijo.game.gameobjects.Mission;
import kalaveijo.game.gameobjects.MissionWave;
import kalaveijo.game.gameobjects.SpawnTile;
import kalaveijo.game.gameobjects.Unit;
import kalaveijo.game.util.MapLocation;

/*
 * Handles game progression, kinda works like dungeon master
 */
public class GameManager {

	private TemplateManager templateManager;
	private ObjectManager objectManager;
	private Mission currentMission;
	private boolean isBuildPhase = true;
	private boolean playerEndedBuildPhase = false;
	private boolean playerHasBeenRewarded = true;
	private int waveNumber = 1;
	private int amountOfWaves = 1;

	public GameManager(ObjectManager om, TemplateManager tm) {
		this.templateManager = tm;
		this.objectManager = om;
	}

	// asses current game situation
	// handles wave changes, mission changes etc.
	// called in GameThread.tick();
	public void assesGameSituation() {

		checkIfBuildPhase();

		shouldSpawnNextWave();

	}

	public void changeMission(String missionName) {
		this.currentMission = templateManager.createMission(missionName);
		// instantiate new objects from templates

		// remove previous objects from object manager
		objectManager.getEnemyUnits().clear();
		objectManager.getPlayerUnits().clear();
		objectManager.getMap().clear();
		objectManager.getMapUnits().clear();
		objectManager.resetPlayer();

		// add objects to object manager
		objectManager.getMap().add(currentMission.getMap());
		amountOfWaves = currentMission.getWaveList().size();
		waveNumber = 0;
		
		// Gameplay Haxors
		spawnHQ(2,5);
		objectManager.addToAboveEffectList(new TimedShowTextEffect(new MapLocation(2,5), objectManager, 5000, "DEFEND!"));
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

	public void endBuildPhase() {
		this.playerEndedBuildPhase = true;
	}

	public boolean isBuildPhase() {
		return this.isBuildPhase;
	}

	/*
	 * Checks if should spawn next wave and then spawns it
	 */
	private void shouldSpawnNextWave() {
		if (objectManager.getEnemyUnits().isEmpty()) {
			if (!isBuildPhase) {
				waveNumber++;
				if (waveNumber <= amountOfWaves) {
					playerHasBeenRewarded = false;
					spawnWave(waveNumber);
				}
			}
		}
	}

	private void checkIfBuildPhase() {
		if (objectManager.getEnemyUnits().isEmpty()) {
			if (!playerEndedBuildPhase) {
				this.isBuildPhase = true;
				//rewardPlayerForAliveUnits();
				rewardPlayerAfterWaves();
			} else {
				this.isBuildPhase = false;
				this.playerEndedBuildPhase = false;
			}
		}
	}

	private void rewardPlayerForAliveUnits() {
		if (!playerHasBeenRewarded) {
			for (Entity e : objectManager.getPlayerUnits()) {
				objectManager.getPlayer().addMoney(2);
				// should spawn gfx effect to sign why units are given money
				playerHasBeenRewarded = true;
			}
		}
	}

	private void rewardPlayerAfterWaves(){
		if (!playerHasBeenRewarded) {
				objectManager.getPlayer().addMoney(9);
				// should spawn gfx effect to sign why units are given money
				playerHasBeenRewarded = true;
		}
	}
	
	private void spawnHQ(int posX, int posY){
		Unit u = templateManager.selectUnitFromTemplates("hq");
		if (u != null) {
			objectManager.spawnPlayerUnit(u, posX, posY);
		}
	}
	
}
