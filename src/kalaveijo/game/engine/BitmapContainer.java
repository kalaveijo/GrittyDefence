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
	public static final int SPAWN = 4;

	public static final int move_left = 5;
	public static final int move_right = 6;
	public static final int move_up = 7;
	public static final int move_down = 8;

	public static final int move_left_up = 9;
	public static final int move_left_down = 10;
	public static final int move_right_up = 11;
	public static final int move_right_down = 12;

	public static final int attack_left = 13;
	public static final int attack_right = 14;
	public static final int attack_up = 15;
	public static final int attack_down = 16;

	public static final int attack_left_up = 17;
	public static final int attack_left_down = 18;
	public static final int attack_right_up = 19;
	public static final int attack_right_down = 20;

	public static final int die_left = 21;
	public static final int die_right = 22;
	public static final int die_up = 23;
	public static final int die_down = 24;

	public static final int die_left_up = 25;
	public static final int die_left_down = 26;
	public static final int die_right_up = 27;
	public static final int die_right_down = 28;

	public static final int idle_left = 29;
	public static final int idle_right = 30;
	public static final int idle_up = 31;
	public static final int idle_down = 32;

	public static final int idle_left_up = 33;
	public static final int idle_left_down = 34;
	public static final int idle_right_up = 35;
	public static final int idle_right_down = 36;

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
