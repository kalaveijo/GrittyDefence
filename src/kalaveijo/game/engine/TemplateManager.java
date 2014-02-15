package kalaveijo.game.engine;

import java.util.ArrayList;

import kalaveijo.game.engine.template.EntityTemplate;
import kalaveijo.game.engine.template.MissionTemplate;
import kalaveijo.game.engine.template.MissionWaveTemplate;

/*
 * Holds all templates and gives out instances
 */
public class TemplateManager {

	private ArrayList<EntityTemplate> entityTemplates = new ArrayList<EntityTemplate>();
	private ArrayList<MissionTemplate> missionTemplates = new ArrayList<MissionTemplate>();
	private ArrayList<MissionWaveTemplate> waveTemplates = new ArrayList<MissionWaveTemplate>();
	private ObjectManager om;

	public TemplateManager(ObjectManager om,
			ArrayList<EntityTemplate> entityTemplates,
			ArrayList<MissionTemplate> missionTemplates,
			ArrayList<MissionWaveTemplate> waveTemplates) {
		this.om = om;
		this.entityTemplates = entityTemplates;
		this.missionTemplates = missionTemplates;
		this.waveTemplates = waveTemplates;
	}

}
