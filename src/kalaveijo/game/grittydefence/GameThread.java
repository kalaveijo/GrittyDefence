package kalaveijo.game.grittydefence;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
	
	private GameSurfaceView cv;
	private SurfaceHolder mHolder;
	private boolean mRun = false;
	private long startTime, lastTime = 0, sleepTime;
	private int i = 0;
	private long perioid = 20;
	private Paint mPaint;
	private boolean firstRun = true;
	
	private ObjectManager om;

	public GameThread(SurfaceHolder sHolder, GameSurfaceView cv, Paint mPaint) {
		this.cv = cv;
		mHolder = sHolder;
		this.mPaint = mPaint;
	}//Constructor

	public void setRunning(boolean run) {
		mRun = run;
	}//setRunning

	public void initializeGame(Canvas c){
		om = new ObjectManager(c, mPaint);
	}//initializeGame
	
	
	/*
		Main Loop of the game
	 */
	public void run() {
		Canvas mCanvas = null;
		while (mRun) {
			mCanvas = mHolder.lockCanvas();
			
			if(firstRun){
				initializeGame(mCanvas);
				cv.loadBitmapsToMapTiles(om.getMap());
				firstRun = false;
			}
			
			if (mCanvas != null) {
				
				startTime = System.currentTimeMillis();
				cv.doDraw(mCanvas, lastTime, om.getMap());
				mHolder.unlockCanvasAndPost(mCanvas); 
				lastTime = System.currentTimeMillis() - startTime;
				sleepTime = perioid - lastTime;
				
				if(sleepTime <= 0){ //if rendering takes a long time, still sleep
					sleepTime = 5;
				}

			}
			try {
				Thread.sleep(lastTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
