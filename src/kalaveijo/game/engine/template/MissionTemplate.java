package kalaveijo.game.engine.template;

import java.util.ArrayList;

import kalaveijo.game.gameobjects.Map;
import kalaveijo.game.gameobjects.Mission;
import kalaveijo.game.gameobjects.MissionWave;

public class MissionTemplate extends Mission {

	MissionTemplate(String name, Map map, ArrayList<MissionWave> waveList) {
		super(name, map, waveList);
	}
	
	public Mission createInstance(){
		return new Mission(this.name, this.map, this.waveList);
	}

}
