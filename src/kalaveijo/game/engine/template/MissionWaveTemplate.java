package kalaveijo.game.engine.template;

import java.util.ArrayList;

import kalaveijo.game.engine.TemplateManager;
import kalaveijo.game.gameobjects.MissionWave;
import kalaveijo.game.gameobjects.Unit;

/*
 * Stores template for single wave. Creates instance when needed
 */
public class MissionWaveTemplate extends MissionWave {

	private String[] units;
	private TemplateManager tm;

	public MissionWaveTemplate(int waveNumber, String name,
			String[] enemyUnitList, TemplateManager tm) {
		super(waveNumber, name, null);
		this.units = enemyUnitList;
		this.tm = tm;
	}

	// parse template and create single disposable wave object with
	// related units
	public MissionWave createInstance() {
		return new MissionWave(this.waveNumber, this.name, createUnitList());
	}

	// parses through unit list and creates instances of everything
	private ArrayList<Unit> createUnitList() {
		ArrayList<Unit> unitList = new ArrayList<Unit>();
		ArrayList<EntityTemplate> unitTemplateList = tm.getEntityTemplates();

		for (int i = 0; i < units.length; i++) {
			for (EntityTemplate template : unitTemplateList) {
				if (template.getName().equals(units[i])) {
					unitList.add(template.createUnit());
				}
			}
		}

		return unitList;
	}
}
