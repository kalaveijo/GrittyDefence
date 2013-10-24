package kalaveijo.game.grittydefence;

import java.util.ArrayList;

import kalaveijo.game.gameobjects.Map;
import kalaveijo.game.gameobjects.MapTile;
import kalaveijo.game.gameobjects.Unit;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.InputEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

/*
 * Handles painting of images, animating and touch listeners
 */
public class GameSurfaceView extends SurfaceView implements OnTouchListener,
		SurfaceHolder.Callback {

	private final boolean DEBUG = true; // enables debug data to this view

	private Paint mPaint;
	private GestureDetector mDetector;
	private GameThread aThread;
	private Context context; // aka GameActivity
	private ArrayList<InputEvent> alIE = new ArrayList<InputEvent>();

	public GameSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();

	}// constructor

	// Initializes animation thread and canvas paints
	private void initView() {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.BLACK);
		mPaint.setTextSize(13);
		mPaint.setTypeface(Typeface.SANS_SERIF);

		getHolder().addCallback(this);
		aThread = new GameThread(getHolder(), this);

		this.setOnTouchListener(this);

		mDetector = new GestureDetector(getContext(),
				new SimpleOnGestureListener() {

					public boolean onDoubleTap(MotionEvent e) {
						invalidate();
						return true;
					}// onDoubleTap

					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						invalidate();
						return true;
					}// onFling

				}); // gesture detector

	}// initView

	/*
	 * Does drawing to canvas, called from animation thread
	 */
	protected void doDraw(Canvas canvas, Long time, ArrayList<Map> al,
			ArrayList<Unit> playerUnits, ArrayList<Unit> enemyUnits) {
		canvas.drawColor(Color.GREEN);

		// draw map
		drawMap(canvas, al);

		// draw units
		drawUnits(canvas, playerUnits, enemyUnits);

		// draw effects

		// draw GUI

		if (DEBUG) {
			canvas.drawText(String.valueOf("ms: " + time), 20, 20, mPaint);
		}// if

	}// doDraw

	protected void drawMap(Canvas c, ArrayList<Map> al) {
		for (Map m : al) {
			MapTile[][] mt = m.getTiles();
			for (int i = 0; i < mt.length; i++) {
				for (int e = 0; e < mt[i].length; e++) {
					mt[i][e].draw(c, mPaint);
				}// for
			}// for
		}// for
	}// drawMap

	protected void drawUnits(Canvas c, ArrayList<Unit> playerUnits,
			ArrayList<Unit> enemyUnits) {

		for (Unit u : playerUnits) {
			u.draw(c, mPaint);
		}// for

		for (Unit u : enemyUnits) {
			u.draw(c, mPaint);
		}// for

	}// drawUnits

	/*
	 * Does drawing to canvas, called from animation thread
	 */
	/*
	 * public void onDraw(Canvas canvas) { super.onDraw(canvas);
	 * canvas.drawColor(Color.GREEN); }
	 */

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		if (!aThread.isAlive()) {
			aThread = new GameThread(getHolder(), this);
		}
		aThread.setRunning(true);
		aThread.start();

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		boolean retry = true;
		aThread.setRunning(false);
		while (retry) {
			try {
				aThread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View,
	 * android.view.MotionEvent)
	 */
	public boolean onTouch(View v, MotionEvent event) {
		mDetector.onTouchEvent(event);
		int action = event.getAction();
		if (action == MotionEvent.ACTION_MOVE) {
			invalidate();
		}
		return true;
	}// onTouch

	// loads Bitmaps to maptiles
	public boolean loadGraphicsToMapTiles(ArrayList<Map> al) {

		for (Map map : al) {
			MapTile[][] mt = map.getTiles();
			for (int i = 0; i < mt.length; i++) {
				for (int e = 0; e < mt[i].length; e++) {
					Bitmap picture = BitmapFactory.decodeResource(
							getResources(), R.drawable.grass_field);
					picture = Bitmap.createScaledBitmap(picture, 40, 40, true);

					mt[i][e].loadBitmap(picture);
				}// for
			}// for

		}// for

		return true;
	}// loadGraphicsToMapTiles

	// General function that handles every graphical object load
	public void loadGraphics(ArrayList<Unit> playerUnits,
			ArrayList<Unit> enemyUnits, ArrayList<Map> map) {
		try {
			loadGraphicsToMapTiles(map);

			// implement

		} catch (Exception e) {
			Log.d("Graphical load error: ", e.getMessage());
		}// catch
	}// loadGraphics

	// overridden to get canvas measurements
	// currently not in use
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
		int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
	}// onMeasure

	public ArrayList<InputEvent> getInputEvents() {
		return this.alIE;
	}
}
