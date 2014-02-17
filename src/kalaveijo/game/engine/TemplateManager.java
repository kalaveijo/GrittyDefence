package kalaveijo.game.engine;

import java.util.ArrayList;

import kalaveijo.game.engine.template.EntityTemplate;
import kalaveijo.game.engine.template.MapTemplate;
import kalaveijo.game.engine.template.MissionTemplate;
import kalaveijo.game.engine.template.MissionWaveTemplate;
import kalaveijo.game.gameobjects.Mission;

/*
 * Holds all templates and gives out instances
 */
public class TemplateManager {

	private ArrayList<EntityTemplate> entityTemplates = new ArrayList<EntityTemplate>();
	private ArrayList<MissionTemplate> missionTemplates = new ArrayList<MissionTemplate>();
	private ArrayList<MissionWaveTemplate> waveTemplates = new ArrayList<MissionWaveTemplate>();
	private ArrayList<MapTemplate> mapTemplates = new ArrayList<MapTemplate>();
	private ObjectManager om;

	public TemplateManager(ObjectManager om,
			ArrayList<EntityTemplate> entityTemplates,
			ArrayList<MissionTemplate> missionTemplates,
			ArrayList<MissionWaveTemplate> waveTemplates,
			ArrayList<MapTemplate> mapTemplates) {
		this.om = om;
		this.entityTemplates = entityTemplates;
		this.missionTemplates = missionTemplates;
		this.waveTemplates = waveTemplates;
		this.mapTemplates = mapTemplates;
	}

	// creates a mission
	public Mission createMission(String missionName) {

		return null;
	}

}
