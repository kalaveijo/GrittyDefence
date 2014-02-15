package kalaveijo.game.engine.template;

import kalaveijo.game.gameobjects.MissionWave;

/*
 * Stores template for single wave. Creates instance when needed
 */
public class MissionWaveTemplate extends MissionWave {

	private String[] units;

	public MissionWaveTemplate(int waveNumber, String name,
			String[] enemyUnitList) {
		super(waveNumber, name, null);
		this.units = enemyUnitList;
	}

	// parse template and create single disposable wave object with
	// related units
	public MissionWave createInstance() {
		// return new MissionWave(this.waveNumber, this.enemyUnitList);
		return null;
	}

}
