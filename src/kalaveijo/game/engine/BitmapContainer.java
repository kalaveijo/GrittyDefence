package kalaveijo.game.engine;

import android.graphics.Bitmap;

/*
 * Keeps references to current bitmap used by Entity
 * Keeps track of changes of references
 */
public class BitmapContainer {

	private Bitmap picture;
	private boolean pictureHasBeenChanged;
	private String name;

	public BitmapContainer(Bitmap pic, String name) {
		changeBitmap(pic);
		this.name = name;
	}

	public void changeBitmap(Bitmap pic) {
		this.picture = pic;
		this.pictureHasBeenChanged = true;
	}

	public void rendered() {
		this.pictureHasBeenChanged = false;
	}

}
