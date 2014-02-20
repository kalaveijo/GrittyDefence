package kalaveijo.game.util;

/*
 * Options
 */
public final class Options {

	public static final boolean DEBUG = true;
	// works also with px size 62
	public static final int TILE_SIZE = 64; // even numbers only, odds cause
											// crash :)
	public static final int GAME_SPEED = 12;
	// desired fps
	public final static int MAX_FPS = 60;
	// maximum number of frames to be skipped
	public final static int MAX_FRAME_SKIPS = 5;
	// the frame period
	public final static int FRAME_PERIOD = 1000 / MAX_FPS;

}
