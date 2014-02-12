package kalaveijo.game.gameobjects;

import kalaveijo.game.engine.BitmapContainer;
import kalaveijo.game.engine.Entity;
import kalaveijo.game.engine.ObjectManager;
import kalaveijo.game.util.Options;
import android.graphics.Canvas;
import android.graphics.Paint;

public class SpawnTile extends Entity {

	public SpawnTile(long id, ObjectManager om, int posX, int posY) {
		super(id, om);
		super.setPosX(posX);
		super.setPosY(posY);
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
}
