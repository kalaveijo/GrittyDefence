package kalaveijo.game.util;

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
}
