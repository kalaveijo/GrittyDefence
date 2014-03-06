package kalaveijo.game.engine.manager;

import java.util.ArrayList;

import kalaveijo.game.engine.template.EntityTemplate;
import kalaveijo.game.engine.template.MapTemplate;
import kalaveijo.game.engine.template.MissionTemplate;
import kalaveijo.game.engine.template.MissionWaveTemplate;
import kalaveijo.game.engine.template.ProjectileTemplate;
import kalaveijo.game.gameobjects.Mission;
import android.util.Log;

/*
 * Holds all templates and gives out instances
 */
public class TemplateManager {

	private ArrayList<EntityTemplate> entityTemplates = new ArrayList<EntityTemplate>();
	private ArrayList<MissionTemplate> missionTemplates = new ArrayList<MissionTemplate>();
	private ArrayList<MissionWaveTemplate> waveTemplates = new ArrayList<MissionWaveTemplate>();
	private ArrayList<MapTemplate> mapTemplates = new ArrayList<MapTemplate>();
	private ArrayList<ProjectileTemplate> projTemplates = new ArrayList<ProjectileTemplate>();
	private ObjectManager om;

	public TemplateManager(ObjectManager om) {
		this.om = om;
	}

	// creates a mission
	public Mission createMission(String missionName) {

		for (MissionTemplate template : missionTemplates) {
			if (template.getName().equals(missionName)) {
				try {
					return template.createInstance();
				} catch (Exception e) {
					Log.d("template", e.getMessage());
					return null;
				}
			}
		}

		return null;
	}

	public ArrayList<EntityTemplate> getEntityTemplates() {
		return entityTemplates;
	}

	public void setEntityTemplates(ArrayList<EntityTemplate> entityTemplates) {
		this.entityTemplates = entityTemplates;
	}

	public ArrayList<MissionTemplate> getMissionTemplates() {
		return missionTemplates;
	}

	public void setMissionTemplates(ArrayList<MissionTemplate> missionTemplates) {
		this.missionTemplates = missionTemplates;
	}

	public ArrayList<MissionWaveTemplate> getWaveTemplates() {
		return waveTemplates;
	}

	public void setWaveTemplates(ArrayList<MissionWaveTemplate> waveTemplates) {
		this.waveTemplates = waveTemplates;
	}

	public ArrayList<MapTemplate> getMapTemplates() {
		return mapTemplates;
	}

	public void setMapTemplates(ArrayList<MapTemplate> mapTemplates) {
		this.mapTemplates = mapTemplates;
	}

}
