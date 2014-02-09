package kalaveijo.game.grittydefence;

import java.util.ArrayList;

import kalaveijo.game.engine.Entity;
import kalaveijo.game.gameobjects.Map;
import kalaveijo.game.gameobjects.MapTile;
import kalaveijo.game.gameobjects.MovementHelper;
import kalaveijo.game.util.Options;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
			MovementHelper[][] mh = map.getHelpers();
			for (int i = 0; i < mt.length; i++) {
				for (int e = 0; e < mt[i].length; e++) {
					Bitmap picture = BitmapFactory.decodeResource(
							getResources(), R.drawable.grass_field);
					picture = Bitmap.createScaledBitmap(picture, 40, 40, true);
					// load map
					mt[i][e].loadBitmap(picture);

					// load helper pictures
					Bitmap[] helper = new Bitmap[4];

					picture = BitmapFactory.decodeResource(getResources(),
							R.drawable.arrow_up);
					picture = Bitmap.createScaledBitmap(picture,
							Options.TILE_SIZE, Options.TILE_SIZE, true);
					helper[MovementHelper.UP] = picture;
					picture = BitmapFactory.decodeResource(getResources(),
							R.drawable.arrow_down);
					picture = Bitmap.createScaledBitmap(picture,
							Options.TILE_SIZE, Options.TILE_SIZE, true);
					helper[MovementHelper.DOWN] = picture;
					picture = BitmapFactory.decodeResource(getResources(),
							R.drawable.arrow_left);
					picture = Bitmap.createScaledBitmap(picture,
							Options.TILE_SIZE, Options.TILE_SIZE, true);
					helper[MovementHelper.LEFT] = picture;
					picture = BitmapFactory.decodeResource(getResources(),
							R.drawable.arrow_right);
					picture = Bitmap.createScaledBitmap(picture,
							Options.TILE_SIZE, Options.TILE_SIZE, true);
					helper[MovementHelper.RIGHT] = picture;

					mh[i][e].loadBitmap(helper);

				}// for
			}// for

		}// for

		return true;
	}// loadGraphicsToMapTiles

	// General function that handles every graphical object load
	public void loadGraphics(ArrayList<Entity> playerUnits,
			ArrayList<Entity> enemyUnits, ArrayList<Map> map) {
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
