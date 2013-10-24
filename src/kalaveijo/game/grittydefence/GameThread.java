package kalaveijo.game.grittydefence;

import kalaveijo.game.gameobjects.ObjectManager;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

	private GameSurfaceView cv;
	private SurfaceHolder mHolder;
	private boolean mRun = false;
	private long startTime, lastTime = 0, sleepTime;
	private long perioid = 20;
	private boolean firstRun = true;

	private ObjectManager om;

	public GameThread(SurfaceHolder sHolder, GameSurfaceView cv) {
		this.cv = cv;
		mHolder = sHolder;
	}// Constructor

	public void setRunning(boolean run) {
		mRun = run;
	}// setRunning

	// Initializes all gamelogic related stuff
	public void initializeGame(Canvas c) {
		om = new ObjectManager();
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
				cv.loadGraphics(om.getPlayerUnits(), om.getEnemyUnits(),
						om.getMap()); // loads images to all
										// gameobjects
				firstRun = false;
			}// if

			if (mCanvas != null) {

				startTime = System.currentTimeMillis();
				cv.doDraw(mCanvas, lastTime, om.getMap());
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

	// Passes user events to gameobjects, needs ArrayList<GuiEvent> as param
	public void handleEvents() {

	}
}
