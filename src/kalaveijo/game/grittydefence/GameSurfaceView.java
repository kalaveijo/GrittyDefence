package kalaveijo.game.grittydefence;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
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
	private Context context;

	public GameSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
		
	}

	//Initializes animation thread and canvas paints
	private void initView() {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG); // reduce the
		mPaint.setColor(Color.BLACK); // jaggedness of lines in graphics
		mPaint.setTextSize(13);
		mPaint.setTypeface(Typeface.SANS_SERIF);

		getHolder().addCallback(this);
		aThread = new GameThread(getHolder(), this, mPaint);
		
		this.setOnTouchListener(this);
		
		mDetector = new GestureDetector(getContext(),
				new SimpleOnGestureListener() {
			
					public boolean onDoubleTap(MotionEvent e) {
						invalidate();
						return true;
					}
					

					public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
						invalidate();
						return true;
					}
					
				});

	}

	/*
	 * Does drawing to canvas, called from animation thread
	 */
	protected void doDraw(Canvas canvas, Long time, ArrayList<Map> al) {	
		canvas.drawColor(Color.GREEN);
		
		drawMap(canvas, al);
		
		if(DEBUG){
			canvas.drawText(String.valueOf("ms: " + time), 20, 20, mPaint);
		}
			
	}
	
	protected void drawMap(Canvas c, ArrayList<Map> al){
		for(Map m : al){
			MapTile[][] mt =  m.getTiles();
			for(int i = 0; i < mt.length; i++){
				for(int e = 0; e < mt[i].length; e++){
					mt[i][e].draw(c);
				}
			}
		}
	}

	/*
	 * Does drawing to canvas, called from animation thread
	 */
	/*
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.GREEN);
	}
	*/

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		if (!aThread.isAlive()) {
			aThread = new GameThread(getHolder(), this, mPaint);
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
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
	 */
	public boolean onTouch(View v, MotionEvent event) {
		mDetector.onTouchEvent(event);
		int action = event.getAction();
		if (action == MotionEvent.ACTION_MOVE) {
			invalidate();
		}
		return true;
	}
	
	public boolean loadBitmapsToMapTiles(ArrayList<Map> al){
		
		
		for(Map map : al){
			MapTile[][] mt =  map.getTiles();
			for(int i = 0; i < mt.length; i++){
				for(int e = 0; e < mt[i].length; e++){
					Bitmap picture = BitmapFactory.decodeResource(getResources(),
							R.drawable.grass_field);
					picture = Bitmap.createScaledBitmap(picture, 40, 40, true);
					
					mt[i][e].loadBitmap(picture);
				}
			}
			
		}
		
		return true;
	}
	
}
