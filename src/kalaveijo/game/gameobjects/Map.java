package kalaveijo.game.gameobjects;

import kalaveijo.game.engine.Entity;
import kalaveijo.game.engine.ObjectManager;
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

	private final int sizeX;
	private final int sizeY;
	private MapTile[][] tiles;
	private MovementHelper[][] helpers;
	private Bitmap fullMap;
	private boolean needsReDraw = true;

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

	public void draw(Canvas c) {

		// if map havent been processed into one bitmap or needs redraw
		if (needsReDraw || fullMap == null) {
			if (fullMap == null) {
				// create bitmap sized of whole map
				fullMap = Bitmap.createBitmap(sizeX * Options.TILE_SIZE, sizeY
						* Options.TILE_SIZE, Bitmap.Config.ARGB_8888);
			}
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

	public int getSizeX() {
		return this.sizeX;
	}

	public int getSizeY() {
		return this.sizeY;
	}
}
