package kalaveijo.game.engine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/*
 * Container for single gui Element
 */
public class GUIElement {

	private BitmapContainerGroup bcg;
	private String name;
	private Point point;

	public GUIElement(BitmapContainerGroup bcg, String name, Point point) {
		this.bcg = bcg;
		this.name = name;
		this.point = point;
	}

	public void draw(Canvas c) {
		if (bcg != null) {
			c.drawBitmap(
					bcg.findBitmapContainerByType(BitmapContainer.move_left)
							.getPicture(), (float) point.x, (float) point.y,
					new Paint());
		} else {
			c.drawRect(point.x, point.y, point.x + 30, point.y + 30,
					new Paint());
			c.drawText(name, point.x + 2, point.y + 2, new Paint());
		}
	}

	public String getName() {
		return name;
	}

	public Point getPoint() {
		return point;
	}

}
