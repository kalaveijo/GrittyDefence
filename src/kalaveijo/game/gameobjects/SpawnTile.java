package kalaveijo.game.gameobjects;

import kalaveijo.game.engine.BitmapContainer;
import kalaveijo.game.engine.BitmapContainerGroup;
import kalaveijo.game.engine.Entity;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.util.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class SpawnTile extends Entity {

	public SpawnTile(long id, ObjectManager om, int posX, int posY) {
		super(id, om);
		super.setPosX(posX);
		super.setPosY(posY);
		super.location = new Point(posX * Options.TILE_SIZE, posY
				* Options.TILE_SIZE);
	}

	public void draw(Canvas c) {
		Paint paint = new Paint();
		if (Options.DEBUG) {
			if (bmContainerGroup != null) {
				BitmapContainer bc = bmContainerGroup
						.findBitmapContainerByType(BitmapContainer.SPAWN);
				if (bc != null) {
					c.drawBitmap(bc.getPicture(), location.x, location.y, paint);
				}

			}
		}
	}

	public void setBitmapContainerGroup(BitmapContainerGroup bcg) {
		this.bmContainerGroup = bcg;
	}
}
