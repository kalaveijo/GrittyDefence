package kalaveijo.game.engine.template;

import java.util.ArrayList;
import java.util.Random;

import kalaveijo.game.engine.manager.TemplateManager;
import kalaveijo.game.gameobjects.Map;
import kalaveijo.game.gameobjects.Mission;
import kalaveijo.game.gameobjects.MissionWave;
import kalaveijo.game.gameobjects.Unit;

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

	public Mission createRandomInstance() throws Exception {
		ArrayList<MissionWave> instantiatedWaveList = createRandomWaveList();
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

	private ArrayList<MissionWave> createRandomWaveList() {

		ArrayList<String> allowedUnits = new ArrayList<String>();
		allowedUnits.add("cow");
		ArrayList<Unit> units = new ArrayList<Unit>();
		ArrayList<MissionWave> waves = new ArrayList<MissionWave>();

		// create 10 different waves
		for (int i = 1; i < 11; i++) {

			// create random amount of sub waves
			for (int e = 0; e < randInt(1, i + 1); e++) {
				// create random amount of units in subwave
				for (int u = 0; u < randInt(1 + e, 6); u++) {

					String randomName = allowedUnits.get(randInt(0,
							allowedUnits.size() - 1));
					ArrayList<EntityTemplate> eTemplates = tm
							.getEntityTemplates();
					for (EntityTemplate et : eTemplates) {
						if (et.getName().equals(randomName)) {
							units.add(et.createUnit());
						}
					}

				}

				waves.add(new MissionWave(i, "randomWave " + 1, units));
				units = new ArrayList<Unit>();

			}

			// add and remove units as game progresses
			if (i == 1)
				allowedUnits.add("farmer");
			if (i == 2)
				allowedUnits.add("rifleman");
			if (i == 4)
				allowedUnits.remove("cow");
			if (i == 7)
				allowedUnits.add("tank");
			if (i == 7)
				allowedUnits.add("rifleman");
			if (i == 7)
				allowedUnits.add("rifleman");
		}
		return waves;
	}

	/**
	 * Returns a pseudo-random number between min and max, inclusive. The
	 * difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 * 
	 * @param min
	 *            Minimum value
	 * @param max
	 *            Maximum value. Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int randInt(int min, int max) {

		// NOTE: Usually this should be a field rather than a method
		// variable so that it is not re-seeded every call.
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

}
