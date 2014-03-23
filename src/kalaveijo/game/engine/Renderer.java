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

		for (Entity p : om.getLiveProjectiles()) {
			p.draw(c);
		}

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

		// load default sprites
		loadDefaultSprites(BitmapContainers);

		// loads rest of the sprites
		loadSprites(BitmapContainers);

		// here be haxorz
		debugBitmapLoad(BitmapContainers);

	}

	public void pairContainersWithEntities() {

		// loop through all entities and pair correct containers
		for (Entity e : tm.getEntityTemplates()) {
			for (BitmapContainerGroup bcg : bitmapContainers) {
				// see if can find correct container group
				if (e.getBitmapContainerGroup().equals(bcg.getName())) {
					e.setBmContainerGroup(bcg);
				} else {
					// if not, find default group
					for (BitmapContainerGroup bcg2 : bitmapContainers) {
						if (bcg2.getName().equals("default")) {
							e.setBmContainerGroup(bcg2);
						}
					}
				}
			}

		}

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
	private void loadDefaultSprites(
			ArrayList<BitmapContainerGroup> bitmapContainers) {

		// create container
		BitmapContainerGroup defaultGroup = new BitmapContainerGroup("default");
		// load spritesheet
		Bitmap defaultSpriteSheet = BitmapFactory.decodeResource(
				cv.getResources(), R.drawable.default_spritesheet);

		defaultGroup = parseSpritesheetIntoContainers(defaultGroup,
				defaultSpriteSheet);
		bitmapContainers.add(defaultGroup);

	}

	// hacks and slashes sheet into separate
	private BitmapContainerGroup parseSpritesheetIntoContainers(
			BitmapContainerGroup containerGroup, Bitmap spriteSheet) {

		/*
		 * Bitmap parsedBitmap = Bitmap.createBitmap(Options.TILE_SIZE,
		 * Options.TILE_SIZE, Bitmap.Config.ARGB_8888);
		 */
		// Canvas canvas = new Canvas(parsedBitmap);
		// canvas.save();
		// Paint paint = new Paint();

		for (int row = 0; row < 20; row++) {
			for (int frame = 0; frame < 4; frame++) {

				String name = "";
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
				Bitmap singleBitmap = Bitmap.createBitmap(spriteSheet, frame
						* Options.TILE_SIZE, row * Options.TILE_SIZE,
						Options.TILE_SIZE, Options.TILE_SIZE);

				//

				// create special cases where need to flip sprite

				switch (row) {

				// move_left
				case 0:
					type = BitmapContainer.move_left;
					pairContainerWithGroup(containerGroup, type, frame, name,
							singleBitmap);
					type = BitmapContainer.move_right;
					pairContainerWithGroup(containerGroup, type, frame, name,
							flip(singleBitmap));
					break;
				// move_up
				case 1:
					type = BitmapContainer.move_up;
					pairContainerWithGroup(containerGroup, type, frame, name,
							singleBitmap);
					break;
				// move_down
				case 2:
					type = BitmapContainer.move_down;
					pairContainerWithGroup(containerGroup, type, frame, name,
							singleBitmap);
					break;
				// move_left_up
				case 3:
					type = BitmapContainer.move_left_up;
					pairContainerWithGroup(containerGroup, type, frame, name,
							singleBitmap);
					type = BitmapContainer.move_right_up;
					pairContainerWithGroup(containerGroup, type, frame, name,
							flip(singleBitmap));
					break;
				// move_left_down
				case 4:
					type = BitmapContainer.move_left_down;
					pairContainerWithGroup(containerGroup, type, frame, name,
							singleBitmap);
					type = BitmapContainer.move_right_down;
					pairContainerWithGroup(containerGroup, type, frame, name,
							flip(singleBitmap));
					break;
				// attack_left
				case 5:
					type = BitmapContainer.attack_left;
					pairContainerWithGroup(containerGroup, type, frame, name,
							singleBitmap);
					type = BitmapContainer.attack_right;
					pairContainerWithGroup(containerGroup, type, frame, name,
							flip(singleBitmap));
					break;
				// attack_up
				case 6:
					type = BitmapContainer.attack_up;
					pairContainerWithGroup(containerGroup, type, frame, name,
							singleBitmap);
					break;
				// attack_down
				case 7:
					type = BitmapContainer.attack_down;
					pairContainerWithGroup(containerGroup, type, frame, name,
							singleBitmap);
					break;
				// attack_left_up
				case 8:
					type = BitmapContainer.attack_left_up;
					pairContainerWithGroup(containerGroup, type, frame, name,
							singleBitmap);
					type = BitmapContainer.attack_right_up;
					pairContainerWithGroup(containerGroup, type, frame, name,
							flip(singleBitmap));
					break;
				// attack_left_down
				case 9:
					type = BitmapContainer.attack_left_down;
					pairContainerWithGroup(containerGroup, type, frame, name,
							singleBitmap);
					type = BitmapContainer.attack_right_down;
					pairContainerWithGroup(containerGroup, type, frame, name,
							flip(singleBitmap));
					break;
				// die_left
				case 10:
					type = BitmapContainer.die_left;
					pairContainerWithGroup(containerGroup, type, frame, name,
							singleBitmap);
					type = BitmapContainer.die_right;
					pairContainerWithGroup(containerGroup, type, frame, name,
							flip(singleBitmap));
					break;
				// die_up
				case 11:
					type = BitmapContainer.die_up;
					pairContainerWithGroup(containerGroup, type, frame, name,
							singleBitmap);
					break;
				// die_down
				case 12:
					type = BitmapContainer.die_down;
					pairContainerWithGroup(containerGroup, type, frame, name,
							singleBitmap);
					break;
				// die_left_up
				case 13:
					type = BitmapContainer.die_left_up;
					pairContainerWithGroup(containerGroup, type, frame, name,
							singleBitmap);
					type = BitmapContainer.die_right_up;
					pairContainerWithGroup(containerGroup, type, frame, name,
							flip(singleBitmap));
					break;
				// die_left_down
				case 14:
					type = BitmapContainer.die_left_down;
					pairContainerWithGroup(containerGroup, type, frame, name,
							singleBitmap);
					type = BitmapContainer.die_right_down;
					pairContainerWithGroup(containerGroup, type, frame, name,
							flip(singleBitmap));
					break;
				// idle_left
				case 15:
					type = BitmapContainer.idle_left;
					pairContainerWithGroup(containerGroup, type, frame, name,
							singleBitmap);
					type = BitmapContainer.idle_right;
					pairContainerWithGroup(containerGroup, type, frame, name,
							flip(singleBitmap));
					break;
				// idle_up
				case 16:
					type = BitmapContainer.idle_up;
					pairContainerWithGroup(containerGroup, type, frame, name,
							singleBitmap);
					break;

				// idle_down
				case 17:
					type = BitmapContainer.idle_down;
					pairContainerWithGroup(containerGroup, type, frame, name,
							singleBitmap);
					break;
				// idle_left_up
				case 18:
					type = BitmapContainer.idle_left_up;
					pairContainerWithGroup(containerGroup, type, frame, name,
							singleBitmap);
					type = BitmapContainer.idle_right_up;
					pairContainerWithGroup(containerGroup, type, frame, name,
							flip(singleBitmap));
					break;
				// idle_left_down
				case 19:
					type = BitmapContainer.idle_left_down;
					pairContainerWithGroup(containerGroup, type, frame, name,
							singleBitmap);
					type = BitmapContainer.idle_right_down;
					pairContainerWithGroup(containerGroup, type, frame, name,
							flip(singleBitmap));
					break;

				}

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

	// not in use currently
	private String parseCorrectName(int row, int frame) {
		String returnString = "";

		return returnString;
	}

	private void pairContainerWithGroup(BitmapContainerGroup bcg, int type,
			int frame, String name, Bitmap picture) {

		BitmapContainer bc = new BitmapContainer(picture, name, type, frame,
				true, bcg);
		bcg.addBitmapContainer(bc);

	}

	// attempts to load all sprites from templates into bitmapContainerGroups
	private void loadSprites(ArrayList<BitmapContainerGroup> bitmapContainers) {

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
