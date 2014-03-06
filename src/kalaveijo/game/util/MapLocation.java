package kalaveijo.game.util;

/*
 * Used to hold map X Y Z locations
 */
public class MapLocation {

	public final int x;
	public final int y;
	public final int z;

	public MapLocation(int x, int y) {
		this.x = x;
		this.y = y;
		this.z = -1;
	}

	public MapLocation(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

}
