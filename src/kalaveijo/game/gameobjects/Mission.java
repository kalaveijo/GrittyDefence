package kalaveijo.game.gameobjects;

import java.util.ArrayList;

/*
 * Contains all data for single mission, used to set starting values
 */
public class Mission {

	protected String name;
	protected Map map;
	protected ArrayList<MissionWave> waveList;

	public Mission(String name, Map map, ArrayList<MissionWave> waveList) {
		this.name = name;
		this.map = map;
		this.waveList = waveList;
	}

	
	public String getName() {
		return name;
	}

	public Map getMap() {
		return map;
	}

	public ArrayList<MissionWave> getWaveList() {
		return waveList;
	}
	
	public boolean wavesStillExist(int waveNumber){
		for(MissionWave wave : waveList){
			if(wave.getWaveNumber() == waveNumber) return true;
		}
		return false;
	}
}
