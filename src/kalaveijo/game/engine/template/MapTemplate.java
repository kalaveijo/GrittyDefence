package kalaveijo.game.engine.template;

import java.util.ArrayList;

import kalaveijo.game.engine.ObjectManager;
import kalaveijo.game.gameobjects.Map;
import kalaveijo.game.gameobjects.MapTile;
import kalaveijo.game.gameobjects.MovementHelper;
import kalaveijo.game.gameobjects.SpawnTile;

public class MapTemplate extends Map {

	public MapTemplate(long id, ObjectManager om, String name, int x, int y,
			MapTile[][] tiles, MovementHelper[][] helpers,
			ArrayList<SpawnTile> spawners) {
		super(id, name, om, x, y, tiles, helpers, spawners);
	}

	public String getName() {
		return this.name;
	}

	public Map createInstance() {
		MapTile[][] newTiles = reCreateMapTiles();
		return null;
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
		return null;
	}
}
