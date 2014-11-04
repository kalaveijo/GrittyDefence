package kalaveijo.game.util;

/*
 * Options
 */
public final class Options {

	public static final boolean DEBUG = true;
	// works also with px size 62
	public static final int TILE_SIZE = 64; // even numbers only, odds cause
											// crash :)
	public static final int GAME_SPEED = 80; //12
	// bigger speed == slower gamespeed, LOGIC BITCHES
	// desired fps
	public final static int MAX_FPS = 35;
	// maximum number of frames to be skipped
	public final static int MAX_FRAME_SKIPS = 5;
	// the frame period
	public final static int FRAME_PERIOD = 1000 / MAX_FPS;
	// Max number of gui layers
	public final static int MAX_GUI_LAYERS = 5;

	public final static int TOLERANCE = 50;
	
	public static final boolean DRAW_HEALTH = true;
	
	public static final boolean DRAW_TILE_BORDERS = true;

}
