package kalaveijo.game.engine;

import java.util.ArrayList;

import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.engine.manager.TemplateManager;
import kalaveijo.game.engine.template.EntityTemplate;
import kalaveijo.game.engine.template.MapTemplate;
import kalaveijo.game.gameobjects.Map;
import kalaveijo.game.gameobjects.MovementHelper;
import kalaveijo.game.gameobjects.SpawnTile;
import kalaveijo.game.grittydefence.GameSurfaceView;
import kalaveijo.game.grittydefence.R;
import kalaveijo.game.util.Options;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.DisplayMetrics;

/*
 * Handles all rendering and image processing related stuff
 * Contains references to all images
 */
public class Renderer {

	ObjectManager om;
	GameThread gThread;
	ArrayList<BitmapContainerGroup> bitmapContainers;
	GameSurfaceView cv;
	TemplateManager tm;

	public Renderer(ObjectManager om, GameThread gThread, GameSurfaceView cv,
			TemplateManager tm) {
		this.om = om;
		this.gThread = gThread;
		this.cv = cv;
		this.bitmapContainers = new ArrayList<BitmapContainerGroup>();
		this.tm = tm;
	}

	// for some reason this causes heavy load, need to debug later, use
	// render(Canvas canvas) instead
	public Bitmap render() {
		Bitmap renderedImage = null;

		// for each map, render everything
		for (Map map : om.getMap()) {
			renderedImage = Bitmap.createBitmap(map.getSizeX()
					* Options.TILE_SIZE, map.getSizeY() * Options.TILE_SIZE,
					Bitmap.Config.ARGB_8888);

			Canvas temporaryCanvas = new Canvas(renderedImage);

			// draw map
			drawMap(temporaryCanvas);

			// draw units
			drawUnits(temporaryCanvas);

			// draw effects

			// draw GUI

			if (Options.DEBUG) {
				int fps = gThread.getFPS();
				Paint mPaint = new Paint();
				if (fps != 0)
					temporaryCanvas.drawText(String.valueOf("FPS: " + fps), 20,
							20, mPaint);
			}// if

		}

		return renderedImage;
	}

	//
	public void render(Canvas canvas) {

		// draw map
		drawMap(canvas);

		// draw units
		drawUnits(canvas);

		// draw effects

		// draw GUI

		if (Options.DEBUG) {
			int fps = gThread.getFPS();
			Paint mPaint = new Paint();
			if (fps != 0)
				canvas.drawText(String.valueOf("FPS: " + fps), 20, 20, mPaint);
		}// if

	}

	protected void drawMap(Canvas c, ArrayList<Map> al) {
		for (Map m : al) {
			m.draw(c);
		}
	}// drawMap

	protected void drawUnits(Canvas c) {

		for (Entity u : om.getPlayerUnits()) {
			u.draw(c);
		}// for

		for (Entity u : om.getEnemyUnits()) {
			u.draw(c);
		}// for

	}// drawUnits

	protected void drawMap(Canvas c) {
		for (Map m : om.getMap()) {
			m.draw(c);
		}
	}// drawMap

	// handles loading of all bitmaps to memory
	public void load() {
		// load all bitmaps to memory
		loadBitmaps(bitmapContainers);

		// pair all entities with correct BitmapContainerGroups
		pairContainersWithEntities();

	}

	public BitmapContainerGroup findContainer(String name) {
		for (BitmapContainerGroup bcg : bitmapContainers) {
			if (bcg.getName().equals(name)) {
				return bcg;
			}
		}
		return null;
	}

	// needs argument where to load all container groups
	public void loadBitmaps(ArrayList<BitmapContainerGroup> BitmapContainers) {

		// load xml file
		ArrayList<String> fileNameList = createRequiredContainerList();

		// parse xml file and create container for all sprite groups
		// here be haxorz
		debugBitmapLoad(BitmapContainers);

	}

	public void pairContainersWithEntities() {

		// loop through all entities and pair correct containers
		debugPairContainers();

	}

	// might actually to spin this into proper function that takes care of map
	// related pairings
	public void debugPairContainers() {

		ArrayList<Map> al = om.getMap();

		for (Map map : al) {
			MovementHelper[][] mh = map.getHelpers();
			for (int i = 0; i < map.getSizeX(); i++) {
				for (int e = 0; e < map.getSizeY(); e++) {
					BitmapContainerGroup bcg = findContainer("MovementHelper");
					if (bcg != null) {
						mh[i][e].setBmContainerGroup(bcg);
					}
				}// for
			}// for

			ArrayList<SpawnTile> alst = map.getSpawners();
			for (SpawnTile st : alst) {
				BitmapContainerGroup bcg = findContainer("SpawnTile");
				if (bcg != null) {
					st.setBmContainerGroup(bcg);
				}
			}
		}

		ArrayList<MapTemplate> altem = tm.getMapTemplates();

		for (Map map : altem) {
			MovementHelper[][] mh = map.getHelpers();
			for (int i = 0; i < map.getSizeX(); i++) {
				for (int e = 0; e < map.getSizeY(); e++) {
					BitmapContainerGroup bcg = findContainer("MovementHelper");
					if (bcg != null) {
						mh[i][e].setBmContainerGroup(bcg);
					}
				}// for
			}// for

			ArrayList<SpawnTile> alst = map.getSpawners();
			for (SpawnTile st : alst) {
				BitmapContainerGroup bcg = findContainer("SpawnTile");
				if (bcg != null) {
					st.setBmContainerGroup(bcg);
				}
			}
		}

	}

	// reads all entity templates and creates list with filenames
	private ArrayList<String> createRequiredContainerList() {
		ArrayList<String> fileList = new ArrayList<String>();
		ArrayList<EntityTemplate> entityList = tm.getEntityTemplates();

		for (EntityTemplate et : entityList) {
			fileList.add(et.getBitmapContainerGroup());
		}

		return fileList;
	}

	private void parseFilesIntoBitmapContainerGroups(ArrayList<String> fileList) {

		// go through all filenames
		for (String fileName : fileList) {

		}

	}

	// loads default spritesheet for entities who dont have
	private void loadDefaultSprites() {

		// create container
		BitmapContainerGroup defaultGroup = new BitmapContainerGroup("default");
		// load spritesheet
		Bitmap defaultSpriteSheet = BitmapFactory.decodeResource(
				cv.getResources(), R.drawable.default_spritesheet);

		defaultGroup = parseSpritesheetIntoContainers(defaultGroup,
				defaultSpriteSheet);

	}

	// hacks and slashes sheet into separate
	private BitmapContainerGroup parseSpritesheetIntoContainers(
			BitmapContainerGroup containerGroup, Bitmap spriteSheet) {

		Bitmap parsedBitmap = Bitmap.createBitmap(Options.TILE_SIZE,
				Options.TILE_SIZE, Bitmap.Config.ARGB_8888);
		// Canvas canvas = new Canvas(parsedBitmap);
		// canvas.save();
		// Paint paint = new Paint();

		for (int row = 0; row < 20; row++) {
			for (int frame = 0; frame < 4; frame++) {

				String name;
				int type;

				/*
				 * Rect source = new Rect(row * Options.TILE_SIZE, frame
				 * Options.TILE_SIZE, (row * Options.TILE_SIZE) +
				 * Options.TILE_SIZE, (frame * Options.TILE_SIZE) +
				 * Options.TILE_SIZE); Rect destination = new Rect(0, 0,
				 * Options.TILE_SIZE, Options.TILE_SIZE);
				 * canvas.drawBitmap(parsedBitmap, source, destination, paint);
				 * canvas.restore();
				 */

				// create new bitmap for container
				Bitmap singleBitmap = Bitmap.createBitmap(parsedBitmap, row
						* Options.TILE_SIZE, frame * Options.TILE_SIZE,
						Options.TILE_SIZE, Options.TILE_SIZE);

				// BitmapContainer = new BitmapContainer(singleBitmap);

			}
		}

		return containerGroup;
	}

	// might actually to spin this into proper function that takes care of map
	// piece loadings
	public void debugBitmapLoad(ArrayList<BitmapContainerGroup> bitmapContainers) {
		BitmapContainerGroup bmg = new BitmapContainerGroup("MovementHelper");
		Bitmap picture;

		picture = BitmapFactory.decodeResource(cv.getResources(),
				R.drawable.arrow_up);
		picture = Bitmap.createScaledBitmap(picture, Options.TILE_SIZE,
				Options.TILE_SIZE, true);
		bmg.addBitmapContainer(new BitmapContainer(picture, "Up_arrow",
				BitmapContainer.UP, 0, false, bmg));

		picture = BitmapFactory.decodeResource(cv.getResources(),
				R.drawable.arrow_down);
		picture = Bitmap.createScaledBitmap(picture, Options.TILE_SIZE,
				Options.TILE_SIZE, true);
		bmg.addBitmapContainer(new BitmapContainer(picture, "Down_arrow",
				BitmapContainer.DOWN, 0, false, bmg));

		picture = BitmapFactory.decodeResource(cv.getResources(),
				R.drawable.arrow_left);
		picture = Bitmap.createScaledBitmap(picture, Options.TILE_SIZE,
				Options.TILE_SIZE, true);
		bmg.addBitmapContainer(new BitmapContainer(picture, "Left_arrow",
				BitmapContainer.LEFT, 0, false, bmg));

		picture = BitmapFactory.decodeResource(cv.getResources(),
				R.drawable.arrow_right);
		picture = Bitmap.createScaledBitmap(picture, Options.TILE_SIZE,
				Options.TILE_SIZE, true);
		bmg.addBitmapContainer(new BitmapContainer(picture, "Right_arrow",
				BitmapContainer.RIGHT, 0, false, bmg));

		bitmapContainers.add(bmg);
		//
		bmg = new BitmapContainerGroup("SpawnTile");
		picture = BitmapFactory.decodeResource(cv.getResources(),
				R.drawable.spawn);
		picture = Bitmap.createScaledBitmap(picture, Options.TILE_SIZE,
				Options.TILE_SIZE, true);
		bmg.addBitmapContainer(new BitmapContainer(picture, "spawn",
				BitmapContainer.SPAWN, 0, false, bmg));

		bitmapContainers.add(bmg);
	}

	private String parseCorrectName(int row, int frame) {
		String returnString = "";

		return returnString;
	}

	// courtesy of
	// http://stackoverflow.com/questions/7925278/drawing-mirrored-bitmaps-in-android
	private Bitmap flip(Bitmap d) {
		Matrix m = new Matrix();
		m.preScale(-1, 1);
		Bitmap dst = Bitmap.createBitmap(d, 0, 0, d.getWidth(), d.getHeight(),
				m, false);
		dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
		return dst;
	}
}
