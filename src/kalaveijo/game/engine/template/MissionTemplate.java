package kalaveijo.game.engine.template;

import kalaveijo.game.gameobjects.Map;
import kalaveijo.game.gameobjects.Mission;

public class MissionTemplate extends Mission {

	String[] waveList;

	MissionTemplate(String name, Map map, String[] waveList) {
		super(name, map, null);
		this.waveList = waveList;
	}

	public Mission createInstance() {
		// return new Mission(this.name, this.map, this.waveList);
		return null;
	}

}
