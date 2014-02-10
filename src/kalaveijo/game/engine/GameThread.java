package kalaveijo.game.engine;

import kalaveijo.game.gameobjects.Unit;
import kalaveijo.game.grittydefence.GameSurfaceView;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

	private GameSurfaceView cv;
	private SurfaceHolder mHolder;
	private boolean mRun = false;
	private long startTime, lastTime = 0, sleepTime;
	private long perioid = 20;
	private boolean firstRun = true;
	private Renderer renderer;

	private ObjectManager objectManager;

	public GameThread(SurfaceHolder sHolder, GameSurfaceView cv) {
		this.cv = cv;
		mHolder = sHolder;
	}// Constructor

	public void setRunning(boolean run) {
		mRun = run;
	}// setRunning

	// Initializes all gamelogic related stuff
	public void initializeGame(Canvas c) {
		objectManager = new ObjectManager();
		renderer = new Renderer(objectManager, this, cv);
		Unit rm = new Unit(objectManager.getNextFreeId(), objectManager);
		objectManager.spawnPlayerUnit(rm, 4, 4);
		rm = new Unit(objectManager.getNextFreeId(), objectManager);
		objectManager.spawnPlayerUnit(rm, 15, 4);
	}// initializeGame

	/*
	 * Main Loop of the game
	 */
	public void run() {
		Canvas mCanvas = null;
		while (mRun) {
			mCanvas = mHolder.lockCanvas();

			if (firstRun) { //
				initializeGame(mCanvas); // loads all game objects

				// cv.loadGraphics(objectManager.getPlayerUnits(),
				// objectManager.getEnemyUnits(), objectManager.getMap()); //
				// loads
				// images
				// to
				// all
				// //
				// gameobjects

				renderer.load();
				firstRun = false;
			}// if

			if (mCanvas != null) {

				handleEvents(); // fetches user input
				objectManager.tick();
				startTime = System.currentTimeMillis();
				renderer.render(mCanvas);
				mHolder.unlockCanvasAndPost(mCanvas);
				lastTime = System.currentTimeMillis() - startTime;
				sleepTime = perioid - lastTime;

				if (sleepTime <= 0) { // if rendering takes a long time, still
										// sleep
					sleepTime = 5;
				}// if

			}// if
			try {
				Thread.sleep(lastTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// catch
		}// while
	}// run

	public long getLastTime() {
		return this.lastTime;
	}

	// Passes user events to gameobjects, needs ArrayList<GuiEvent> as param
	public void handleEvents() {

	}
}
