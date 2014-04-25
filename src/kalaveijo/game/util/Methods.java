package kalaveijo.game.util;

import kalaveijo.game.engine.BitmapContainer;
import kalaveijo.game.engine.Entity;

/*
 * Contains handy methods
 */
abstract public class Methods {

	// resizes array and appends object
	static public Object[] appendAndResizeArray(Object[] arrayToResize,
			Object objectToAppend) {
		Object[] b = new Object[arrayToResize.length + 1];

		for (int i = 0; i < arrayToResize.length; i++) {
			if (arrayToResize[i] != null) {
				b[i] = arrayToResize[i];
			} else {
				b[i] = objectToAppend;
			}

		}
		return b;
	}

	static public int convertDirectionToBitmapTypeDie(int type) {
		switch (type) {
		case Entity.UP:
			return BitmapContainer.die_up;
		case Entity.UP_RIGHT:
			return BitmapContainer.die_right_up;
		case Entity.RIGHT:
			return BitmapContainer.die_right;
		case Entity.DOWN_RIGHT:
			return BitmapContainer.die_right_down;
		case Entity.DOWN:
			return BitmapContainer.die_down;
		case Entity.DOWN_LEFT:
			return BitmapContainer.die_left_down;
		case Entity.LEFT:
			return BitmapContainer.die_left;
		case Entity.UP_LEFT:
			return BitmapContainer.die_left_up;
		}
		return BitmapContainer.die_left;
	}
}
