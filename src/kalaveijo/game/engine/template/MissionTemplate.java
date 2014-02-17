package kalaveijo.game.engine.template;

import kalaveijo.game.gameobjects.Mission;

public class MissionTemplate extends Mission {

	String[] waveList;
	String map;

	public MissionTemplate(String name, String map, String[] waveList) {
		super(name, null, null);
		this.map = map;
		this.waveList = waveList;
	}

	public Mission createInstance() {
		// return new Mission(this.name, this.map, this.waveList);
		return null;
	}

}
