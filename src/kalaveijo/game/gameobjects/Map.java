package kalaveijo.game.gameobjects;

import kalaveijo.game.util.Options;
import android.graphics.Point;

/*
 * Keeps track of maptiles
 */
public class Map extends Unit {

	private final int sizeX;
	private final int sizeY;
	private MapTile[][] tiles;
	private MovementHelper[][] helpers;

	// default constructor
	public Map(long id, ObjectManager om) {
		super(id, om);
		sizeX = 20;
		sizeY = 20;
		tiles = new MapTile[sizeX][sizeY];
		helpers = new MovementHelper[sizeX][sizeY];

		for (int i = 0; i < sizeX; i++) {
			for (int e = 0; e < sizeY; e++) {
				tiles[i][e] = new MapTile(om.getNextFreeId(), om, new Point(
						(i * Options.TILE_SIZE), (e * Options.TILE_SIZE)), 0);
			}// for
		}// for
		placeHelpers();

	}// Constructor

	public Map(long id, ObjectManager om, int x, int y) {
		super(id, om);
		sizeX = x;
		sizeY = y;
		tiles = new MapTile[sizeX][sizeY];
		helpers = new MovementHelper[sizeX][sizeY];
		for (int i = 0; i < sizeX; i++) {
			for (int e = 0; e < sizeY; e++) {
				tiles[i][e] = new MapTile(om.getNextFreeId(), om, new Point(
						(i * Options.TILE_SIZE), (e * Options.TILE_SIZE)), 0);
			}// for
		}// for
		placeHelpers();
	}// constructor with size parameters given

	public MapTile[][] getTiles() {
		return this.tiles;
	}// getTiles

	public MovementHelper[][] getHelpers() {
		return this.helpers;
	}

	/*
	 * places helpers to map NOTE: in the future helpers are read from file,
	 * this is just hardcoded
	 */
	private void placeHelpers() {

		int direction = MovementHelper.RIGHT;

		for (int i = 0; i < sizeX; i++) {
			for (int e = 0; e < sizeY; e++) {

				// if we're placing helper to lefternmost part
				if (i == 0) {
					direction = MovementHelper.RIGHT;
					// if we're placing helper to righternmost part
				} else if (i == (sizeX - 1)) {
					direction = MovementHelper.LEFT;
				}

				if (i == 5 && e == 4)
					direction = MovementHelper.DOWN;
				if (i == 5 && e == 5)
					direction = MovementHelper.LEFT;
				if (i == 4 && e == 5)
					direction = MovementHelper.UP;

				helpers[i][e] = new MovementHelper(om.getNextFreeId(), om,
						direction, new Point((i * Options.TILE_SIZE),
								(e * Options.TILE_SIZE)));
			}// for
		}// for
	}

}
