package kalaveijo.game.gameobjects;

import java.util.ArrayList;

/*
 * Contains data for a single wave in mission
 */
public class MissionWave {

	private int waveNumber;
	private ArrayList<Unit> enemyUnitList;

	MissionWave(int waveNumber, ArrayList<Unit> enemyUnitList) {
		this.waveNumber = waveNumber;
		this.enemyUnitList = enemyUnitList;
	}

	public int getWaveNumber() {
		return waveNumber;
	}

	public ArrayList<Unit> getEnemyUnitList() {
		return this.enemyUnitList;
	}

}
