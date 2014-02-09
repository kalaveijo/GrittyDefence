package kalaveijo.game.engine;

import android.graphics.Bitmap;

/*
 * Keeps references to current bitmap used by Entity
 * Keeps track of changes of references
 */
public class BitmapContainer {

	private Bitmap picture;
	private boolean pictureHasBeenChanged;
	private boolean alwaysRender;
	private String name;
	private int type;

	private int frame;
	private BitmapContainerGroup group;

	// declarations of different types
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;

	public BitmapContainer(Bitmap pic, String name, int type, int frame,
			boolean alwaysRender, BitmapContainerGroup group) {
		changeBitmap(pic);
		this.name = name;
		this.alwaysRender = alwaysRender; // not in use currently
		this.type = type;
		this.frame = frame;
		this.group = group;
	}

	public void changeBitmap(Bitmap pic) {
		this.picture = pic;
		this.pictureHasBeenChanged = true;
	}

	// if we want to optimize, then boolean insed can be leaved false so it does
	// not get rendered again needlesly
	public void rendered() {
		this.pictureHasBeenChanged = false;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

	public int getFrame() {
		return frame;
	}

	public Bitmap getPicture() {
		return picture;
	}

}
