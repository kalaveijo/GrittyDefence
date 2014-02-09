package kalaveijo.game.engine;

import java.util.ArrayList;

import kalaveijo.game.gameobjects.Map;
import kalaveijo.game.grittydefence.GameThread;
import kalaveijo.game.util.Options;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/*
 * Handles all rendering and image processing related stuff
 * Contains references to all images
 */
public class Renderer {

	ObjectManager om;
	GameThread gThread;

	public Renderer(ObjectManager om, GameThread gThread) {
		this.om = om;
		this.gThread = gThread;
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
				long time = gThread.getLastTime();
				Paint mPaint = new Paint();
				if (time != 0)
					time = 1000 / time; // converting loop duration to fps
				temporaryCanvas.drawText(String.valueOf("FPS: " + time), 20,
						20, mPaint);
			}// if

		}

		return renderedImage;
	}

	public void render(Canvas canvas) {

		// draw map
		drawMap(canvas);

		// draw units
		drawUnits(canvas);

		// draw effects

		// draw GUI

		if (Options.DEBUG) {
			long time = gThread.getLastTime();
			Paint mPaint = new Paint();
			if (time != 0)
				time = 1000 / time; // converting loop duration to fps
			canvas.drawText(String.valueOf("FPS: " + time), 20, 20, mPaint);
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
}
