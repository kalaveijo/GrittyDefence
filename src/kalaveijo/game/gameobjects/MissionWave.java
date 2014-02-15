package kalaveijo.game.gameobjects;

import java.util.ArrayList;

/*
 * Contains data for a single wave in mission
 */
public class MissionWave {

	protected int waveNumber;
	protected ArrayList<Unit> enemyUnitList;
	protected String name;

	public MissionWave(int waveNumber, String name,
			ArrayList<Unit> enemyUnitList) {
		this.waveNumber = waveNumber;
		this.enemyUnitList = enemyUnitList;
		this.name = name;
	}

	public int getWaveNumber() {
		return waveNumber;
	}

	public ArrayList<Unit> getEnemyUnitList() {
		return this.enemyUnitList;
	}

}
