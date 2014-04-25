package kalaveijo.game.gameobjects;

import java.util.ArrayList;

import kalaveijo.game.engine.Entity;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.util.MapLocation;
import kalaveijo.game.util.Options;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/*
 * Keeps track of maptiles
 */
public class Map extends Entity {

	protected final int sizeX;
	protected final int sizeY;
	protected MapTile[][] tiles;
	protected MovementHelper[][] helpers;
	protected ArrayList<SpawnTile> spawners;
	protected Bitmap fullMap;
	protected boolean needsReDraw = true;

	// default constructor
	public Map(long id, ObjectManager om) {
		super(id, om);
		sizeX = 20;
		sizeY = 20;
		tiles = new MapTile[sizeX][sizeY];
		helpers = new MovementHelper[sizeX][sizeY];
		spawners = new ArrayList<SpawnTile>();

		placeMapTiles();
		placeHelpers();

	}// Constructor

	public Map(long id, ObjectManager om, int x, int y) {
		super(id, om);
		sizeX = x;
		sizeY = y;
		tiles = new MapTile[sizeX][sizeY];
		helpers = new MovementHelper[sizeX][sizeY];
		spawners = new ArrayList<SpawnTile>();

		placeMapTiles();
		placeHelpers();
	}// constructor with size parameters given

	public Map(long id, String name, ObjectManager om, int x, int y,
			MapTile[][] tiles, MovementHelper[][] helpers,
			ArrayList<SpawnTile> spawners) {
		super(id, om);
		this.name = name;
		this.sizeX = x;
		this.sizeY = y;
		this.tiles = tiles;
		this.helpers = helpers;
		this.spawners = spawners;
	}

	public MapTile[][] getTiles() {
		return this.tiles;
	}// getTiles

	public MovementHelper[][] getHelpers() {
		return this.helpers;
	}

	public void draw(Canvas c) {

		// if map havent been processed into one bitmap or needs redraw
		if (needsReDraw || fullMap == null) {
			// if (fullMap == null) {
			// create bitmap sized of whole map
			fullMap = Bitmap.createBitmap(sizeX * Options.TILE_SIZE, sizeY
					* Options.TILE_SIZE, Bitmap.Config.ARGB_8888);
			// }
			// draw map into fullMap
			Canvas temporaryCanvas = new Canvas(fullMap);
			temporaryCanvas.drawColor(Color.GREEN);
			for (int i = 0; i < tiles.length; i++) {
				for (int e = 0; e < tiles[i].length; e++) {
					tiles[i][e].draw(temporaryCanvas);
					if (Options.DEBUG) {
						helpers[i][e].draw(temporaryCanvas);
					}
				}// for
			}// for

			// draw spawners
			if (Options.DEBUG) {
				for (SpawnTile st : spawners) {
					st.draw(temporaryCanvas);
				}
			}

			needsReDraw = false;
			c.drawBitmap(fullMap, 0, 0, new Paint());
		} else {
			// if no need to touch background
			c.drawBitmap(fullMap, 0, 0, new Paint());
		}

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

	private void placeMapTiles() {
		for (int i = 0; i < sizeX; i++) {
			for (int e = 0; e < sizeY; e++) {
				tiles[i][e] = new MapTile(om.getNextFreeId(), om, new Point(
						(i * Options.TILE_SIZE), (e * Options.TILE_SIZE)), 0);
			}// for
		}// for
	}

	public int getSizeX() {
		return this.sizeX;
	}

	public int getSizeY() {
		return this.sizeY;
	}

	public ArrayList<SpawnTile> getSpawners() {
		return spawners;
	}

	public MapTile getTile(MapLocation ml) {

		for (int i = 0; i < sizeX; i++) {
			for (int e = 0; e < sizeY; e++) {
				if (i == ml.x && e == ml.y) {
					if (ml.x > -1 && ml.y > -1 && ml.x < this.sizeX + 1
							&& ml.y < this.sizeY + 1)
						return tiles[i][e];
				}
			}// for
		}// for
		return null;
	}

	public void forceReDraw() {
		needsReDraw = true;
	}
}
