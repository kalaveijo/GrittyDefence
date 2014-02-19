package kalaveijo.game.engine.template;

import java.util.ArrayList;

import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.engine.manager.TemplateManager;
import kalaveijo.game.gameobjects.Map;
import kalaveijo.game.gameobjects.MapTile;
import kalaveijo.game.gameobjects.MovementHelper;
import kalaveijo.game.gameobjects.SpawnTile;

public class MapTemplate extends Map {

	private TemplateManager tm;

	public MapTemplate(long id, ObjectManager om, String name, int x, int y,
			MapTile[][] tiles, MovementHelper[][] helpers,
			ArrayList<SpawnTile> spawners, TemplateManager tm) {
		super(id, name, om, x, y, tiles, helpers, spawners);
		this.tm = tm;
	}

	public String getName() {
		return this.name;
	}

	public Map createInstance() {
		MapTile[][] newTiles = reCreateMapTiles();
		MovementHelper[][] newHelpers = reCreateMovementHelpers();
		ArrayList<SpawnTile> newSpawns = reCreateSpawners();

		Map newMap = new Map(om.getNextFreeId(), this.name, om, this.sizeX,
				this.sizeY, newTiles, newHelpers, newSpawns);

		return newMap;
	}

	private MapTile[][] reCreateMapTiles() {

		MapTile[][] newTiles = new MapTile[getSizeX()][getSizeY()];

		for (int i = 0; i < getSizeX(); i++) {
			for (int e = 0; e < getSizeY(); e++) {
				newTiles[i][e] = new MapTile(om.getNextFreeId(), om,
						tiles[i][e].getLocation(), tiles[i][e].getTileType());
			}
		}
		return newTiles;
	}

	private MovementHelper[][] reCreateMovementHelpers() {

		MovementHelper[][] newHelpers = new MovementHelper[getSizeX()][getSizeY()];

		for (int i = 0; i < getSizeX(); i++) {
			for (int e = 0; e < getSizeY(); e++) {
				newHelpers[i][e] = new MovementHelper(om.getNextFreeId(), om,
						helpers[i][e].getDirection(),
						helpers[i][e].getLocation());
			}
		}

		return newHelpers;
	}

	private ArrayList<SpawnTile> reCreateSpawners() {
		ArrayList<SpawnTile> newSpawns = new ArrayList<SpawnTile>();

		for (SpawnTile spawn : spawners) {
			newSpawns.add(new SpawnTile(om.getNextFreeId(), om,
					spawn.getPosX(), spawn.getPosY()));
		}

		return newSpawns;
	}
}
