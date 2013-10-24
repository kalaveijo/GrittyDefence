package kalaveijo.game.gameobjects;

import kalaveijo.game.util.Options;
import android.graphics.Point;

/*
 * Keeps track of maptiles
 */
public class Map {

	private final int sizeX;
	private final int sizeY;
	private MapTile[][] tiles;

	// default constructor
	public Map() {
		sizeX = 20;
		sizeY = 20;
		tiles = new MapTile[sizeX][sizeY];
		for (int i = 0; i < sizeX; i++) {
			for (int e = 0; e < sizeY; e++) {
				tiles[i][e] = new MapTile(new Point((i * Options.TILE_SIZE),
						(e * Options.TILE_SIZE)), 0);
			}// for
		}// for

	}// Constructor

	public Map(int x, int y) {
		sizeX = x;
		sizeY = y;
		tiles = new MapTile[sizeX][sizeY];
		for (int i = 0; i < sizeX; i++) {
			for (int e = 0; e < sizeY; e++) {
				tiles[i][e] = new MapTile(new Point((i * Options.TILE_SIZE),
						(e * Options.TILE_SIZE)), 0);
			}// for
		}// for
	}// constructor with size parameters given

	public MapTile[][] getTiles() {
		return this.tiles;
	}// getTiles

}
