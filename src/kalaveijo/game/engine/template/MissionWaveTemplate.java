package kalaveijo.game.engine.template;

import java.util.ArrayList;

import kalaveijo.game.gameobjects.MissionWave;
import kalaveijo.game.gameobjects.Unit;

public class MissionWaveTemplate extends MissionWave {

	MissionWaveTemplate(int waveNumber, ArrayList<Unit> enemyUnitList) {
		super(waveNumber, enemyUnitList);
	}
	
	public MissionWave createInstance(){
		return new MissionWave(this.waveNumber, this.enemyUnitList);
	}

}
