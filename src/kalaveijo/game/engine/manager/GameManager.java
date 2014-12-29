package kalaveijo.game.engine.manager;

import java.util.ArrayList;

import kalaveijo.game.effect.EnemyPreviewEffect;
import kalaveijo.game.effect.TimedShowFloatingTextEffect;
import kalaveijo.game.effect.TimedShowTextEffect;
import kalaveijo.game.effect.TimedSpawnWaveEffect;
import kalaveijo.game.engine.Entity;
import kalaveijo.game.engine.template.EntityTemplate;
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
	private MissionWave waveToBeRemoved;
	private int timeInBetweenWavesMilli = 10000;
	private double healthRestorationModifier = 0.3;

	public GameManager(ObjectManager om, TemplateManager tm) {
		this.templateManager = tm;
		this.objectManager = om;
	}

	// asses current game situation
	// handles wave changes, mission changes etc.
	// called in GameThread.tick();
	public void assesGameSituation() {

		checkIfBuildPhase();
		// shouldSpawnNextWave();

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

		// Gameplay Haxors
		spawnHQ(2, 5);
		objectManager.addToAboveEffectList(new TimedShowTextEffect(
				new MapLocation(2, 5), objectManager, 5000, "DEFEND!", 20));
		createEnemyPreviewEffects();
	}

	public boolean spawnWave(int waveNumber) {
		// spawn timer effect for wave spawning if no such timer effect exist
		if (!objectManager.areTimedSpawnWaveEffectsRunning())
			objectManager.addToUnderEffectList(new TimedSpawnWaveEffect(
					new MapLocation(1, 1), objectManager, 10000, this));
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
				waveToBeRemoved = wave;
				return true;
			}
		}
		return false;

	}

	private void spawnWave(String waveName) {

	}

	public void endBuildPhase() {
		this.playerEndedBuildPhase = true;
		this.isBuildPhase = false;
	}

	public boolean isBuildPhase() {
		return this.isBuildPhase;
	}

	/*
	 * Checks if should spawn next wave and then spawns it
	 */
	// private void shouldSpawnNextWave() {
	// if (objectManager.getEnemyUnits().isEmpty()) {
	// if (!isBuildPhase) {
	// if(!objectManager.areTimedSpawnWaveEffectsRunning()){
	// playerHasBeenRewarded = false;
	// spawnWave(waveNumber);
	// }
	// }
	// }
	// }

	private void checkIfBuildPhase() {
		// remove last spawned wave from list
		if (waveToBeRemoved != null)
			currentMission.getWaveList().remove(waveToBeRemoved);
		// check if all enemies are dead TODO ADD CHECK FOR EMPTY
		// TimedSpawnWaveEffects
		if (objectManager.getEnemyUnits().isEmpty()
				&& !objectManager.areTimedSpawnWaveEffectsRunning()
				&& playerEndedBuildPhase) {
			this.isBuildPhase = true;
			this.playerEndedBuildPhase = false;
			// rewardPlayerForAliveUnits();
			rewardPlayerAfterWaves();
			recoverUnitHealth();
			waveNumber++;
			createEnemyPreviewEffects();
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

	private void rewardPlayerAfterWaves() {
		if (!playerHasBeenRewarded) {
			objectManager.getPlayer().addMoney(8);
			// should spawn gfx effect to sign why units are given money
			playerHasBeenRewarded = true;
		}
	}

	private void spawnHQ(int posX, int posY) {
		Unit u = templateManager.selectUnitFromTemplates("hq");
		if (u != null) {
			objectManager.spawnPlayerUnit(u, posX, posY);
		}
	}

	public Mission getCurrentMission() {
		return currentMission;
	}

	public int getWaveNumber() {
		return waveNumber;
	}

	public void startWaves() {

		objectManager.addToAboveEffectList(new TimedShowTextEffect(
				new MapLocation(10, 5), objectManager, 5000, "WAVE: "
						+ waveNumber, 40));

		playerHasBeenRewarded = false;
		endBuildPhase();
		spawnWave(waveNumber);
	}

	/*
	 * Called at the end of wave to restore player unit health
	 */
	private void recoverUnitHealth() {

		/*
		 * for each entity, check corresponding template for max health give 30%
		 * to each unit
		 */

		int restoreAmount = 0;

		for (Entity e : objectManager.getPlayerUnits()) {
			for (EntityTemplate t : templateManager.getEntityTemplates()) {
				if (e.getName().equals(t.getName())) {
					double per = t.getHealth() * healthRestorationModifier;
					restoreAmount = (int) per;
					// we always want to restore something for small maxhealths
					if (restoreAmount == 0)
						restoreAmount = 1;
					int restoreTextInt = restoreAmount;
					restoreAmount = restoreAmount + e.getHealth();
					// if health would go over boundaries, set it to max
					if (restoreAmount + e.getHealth() > t.getHealth()){
						restoreTextInt = t.getHealth() - e.getHealth();
						restoreAmount = t.getHealth();
					}
					// restore health
					e.setHealth(restoreAmount);
					// play effect
					if(restoreTextInt != 0)
					objectManager
							.addToAboveEffectList(new TimedShowFloatingTextEffect(
									new MapLocation(e.getPosX(), e
											.getPosY()), objectManager,
									1500, "+" + restoreTextInt, 20,
									Entity.UP));
				}
			}

		}

	}
	
	/*
	 * Creates small pictures where it shows enemy types and amounts
	 */
	private void createEnemyPreviewEffects(){
		
		ArrayList<MissionWave> nextWave = new ArrayList<MissionWave>();
		ArrayList<Unit> collectedUnits = new ArrayList<Unit>();
		ArrayList<String> collectedUnitNames = new ArrayList<String>();
		int i = 0;
		int y = 0;
		// get all subwaves
		for(MissionWave wave : currentMission.getWaveList()){
			if(wave.getWaveNumber() == waveNumber){
				nextWave.add(wave);
			}
		}
		
		// get all unittypes
		for(MissionWave w : nextWave){
			for(Unit u : w.getEnemyUnitList()){		
					if(!collectedUnitNames.contains(u.getName())){
						collectedUnitNames.add(u.getName());
						collectedUnits.add(u);
					}
			}			
		}
		
		//count all unittypes and create effects
		for(Unit u : collectedUnits){
			for(MissionWave mw : nextWave){
				for(Unit e : mw.getEnemyUnitList()){
					if(u.getName().equals(e.getName())){
						i++;
					}
				}
			}
			
			//create effect
		objectManager.getAboveEffectList().add(new EnemyPreviewEffect(new MapLocation(18, 2 + y), objectManager, this, u, i));
			i=0;
			y = y + 2;
		}
		
	}
}
