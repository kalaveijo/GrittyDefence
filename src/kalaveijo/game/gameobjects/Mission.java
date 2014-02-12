package kalaveijo.game.gameobjects;

import java.util.ArrayList;

/*
 * Contains all data for single mission, used to set starting values
 */
public class Mission {

	private String name;
	private Map map;
	private ArrayList<MissionWave> waveList;

	Mission(String name, Map map, ArrayList<MissionWave> waveList) {
		this.name = name;
		this.map = map;
		this.waveList = waveList;
	}

}
