package kalaveijo.game.engine.template;

import java.util.ArrayList;

import kalaveijo.game.engine.manager.TemplateManager;
import kalaveijo.game.gameobjects.Map;
import kalaveijo.game.gameobjects.Mission;
import kalaveijo.game.gameobjects.MissionWave;

public class MissionTemplate extends Mission {

	private String[] waveList;
	private String map;
	private TemplateManager tm;

	public MissionTemplate(String name, String map, String[] waveList,
			TemplateManager tm) {
		super(name, null, null);
		this.map = map;
		this.waveList = waveList;
		this.tm = tm;
	}

	public Mission createInstance() throws Exception {
		ArrayList<MissionWave> instantiatedWaveList = createWaves();
		Map newMap = createMap();
		if (newMap == null)
			throw new Exception();

		return new Mission(this.name, newMap, instantiatedWaveList);
	}

	private ArrayList<MissionWave> createWaves() {
		ArrayList<MissionWave> instantiatedWaveList = new ArrayList<MissionWave>();
		ArrayList<MissionWaveTemplate> templateList = tm.getWaveTemplates();
		for (int i = 0; i < waveList.length; i++) {
			for (MissionWaveTemplate template : templateList) {
				if (template.getName().equals(waveList[i])) {
					instantiatedWaveList.add(template.createInstance());
				}
			}
		}

		return instantiatedWaveList;
	}

	private Map createMap() {
		ArrayList<MapTemplate> mapTemplate = tm.getMapTemplates();

		for (MapTemplate template : mapTemplate) {
			if (template.getName().equals(map)) {
				Map instantiatedMap = template.createInstance();
				return instantiatedMap;
			}
		}

		return null;
	}

}
