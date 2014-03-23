package kalaveijo.game.engine;

import kalaveijo.game.engine.manager.GameManager;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.engine.manager.TemplateManager;
import kalaveijo.game.engine.template.EntityTemplate;
import kalaveijo.game.gameobjects.Unit;
import kalaveijo.game.grittydefence.GameSurfaceView;
import kalaveijo.game.util.Options;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

	private GameSurfaceView cv;
	private SurfaceHolder mHolder;
	private boolean mRun = false;
	private long startTime, lastTime = 0, sleepTime, fpsTimer = 0;
	private int framesSkipped;
	private int frameCount = 0;
	private int fps = 0;
	private boolean firstRun = true;
	private Renderer renderer;
	private TemplateManager templateManager;
	private ObjectManager objectManager;
	private GameManager gameManager;
	private XMLLoader xmlLoader;

	public GameThread(SurfaceHolder sHolder, GameSurfaceView cv) {
		this.cv = cv;
		mHolder = sHolder;
	}// Constructor

	public void setRunning(boolean run) {
		mRun = run;
	}// setRunning

	// Initializes engine
	public void initializeGame(Canvas c) {
		objectManager = new ObjectManager();
		templateManager = new TemplateManager(objectManager);
		gameManager = new GameManager(objectManager, templateManager);
		xmlLoader = new XMLLoader(objectManager, cv, templateManager);
		renderer = new Renderer(objectManager, this, cv, templateManager);

	}// initializeGame

	/*
	 * Main Loop of the game
	 */
	public void run() {
		Canvas mCanvas;
		while (mRun) {
			mCanvas = null;
			try {
				mCanvas = mHolder.lockCanvas();
				if (firstRun) { //
					initializeGame(mCanvas); // loads all game objects
					templateManager.setProjTemplates(xmlLoader
							.loadProjectiles());
					templateManager
							.setEntityTemplates(xmlLoader.loadEntities());
					templateManager.setMapTemplates(xmlLoader.loadMaps());
					templateManager.setMissionTemplates(xmlLoader
							.loadMissions());
					templateManager.setWaveTemplates(xmlLoader.loadWaves());
					renderer.load();
					gameManager.changeMission("tutorialMission");
					debugCreatePlayerUnit(); // just debug, comment out
					firstRun = false;
				}// if

				startTime = System.currentTimeMillis();

				handleEvents(); // fetches user input
				framesSkipped = 0;

				objectManager.tick();
				renderer.render(mCanvas);

				lastTime = System.currentTimeMillis() - startTime;
				sleepTime = (int) Options.FRAME_PERIOD - lastTime;

				if (sleepTime > 0) {

					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
					}

					while (sleepTime < 0
							&& framesSkipped < Options.MAX_FRAME_SKIPS) {
						// we need to catch up
						// update without rendering
						objectManager.tick();
						// add frame period to check if in next frame
						sleepTime += Options.FRAME_PERIOD;
						framesSkipped++;
					}

					if (framesSkipped > 0) {
						Log.d("frameskip", "Skipped:" + framesSkipped);
					}

				}// if
			} finally {
				if (mCanvas != null) {
					mHolder.unlockCanvasAndPost(mCanvas);
					frameCount++;
					calculateFPS();
				}
			}
		}// run
	}

	public long getLastTime() {
		return this.lastTime;
	}

	public int getFPS() {
		return this.fps;
	}

	private void calculateFPS() {
		if (System.currentTimeMillis() - fpsTimer > 1000) {
			fpsTimer = System.currentTimeMillis();
			fps = frameCount;
			frameCount = 0;
		}
	}

	// Passes user events to gameobjects, needs ArrayList<GuiEvent> as param
	public void handleEvents() {

	}

	public void debugCreatePlayerUnit() {
		for (EntityTemplate et : templateManager.getEntityTemplates()) {
			if (et.getName().equals("machinegunner")) {
				Unit u = et.createUnit();
				objectManager.spawnPlayerUnit(u, 4, 4);
			}

		}
	}
}
