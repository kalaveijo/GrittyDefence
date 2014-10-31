package kalaveijo.game.effect;

import kalaveijo.game.engine.manager.GameManager;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.gameobjects.MissionWave;
import kalaveijo.game.util.MapLocation;

/*
 * Spawns next wave after X milliseconds
 */
public class TimedSpawnWaveEffect extends TimedEffect {

	private GameManager gameManager;
	
	public TimedSpawnWaveEffect(MapLocation startLocation, ObjectManager om,
			int howLong, GameManager gm) {
		super(startLocation, om, howLong);
		this.gameManager = gm;
	}
	
	public void move(){
		if(stopWatch.hasEnoughTimePassed()){
			for(MissionWave wave : gameManager.getCurrentMission().getWaveList()){
				if(wave.getWaveNumber() == gameManager.getWaveNumber()){
					gameManager.spawnWave(gameManager.getWaveNumber());
					stopWatch.start();
				}else{
					//if no waves are left, kill this
					//om.addToRemoveList(this);
				}
			}
		}else if(!gameManager.getCurrentMission().wavesStillExist(gameManager.getWaveNumber())){
			om.addToRemoveList(this);
		}
	}

}
