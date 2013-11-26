package kalaveijo.game.gameobjects;

/*
 * Class under map that tells enemy units where they can move next
 * Each helper only points into certain direction
 */
public class MovementHelper {

	static final int UP = 0;
	static final int DOWN = 1;
	static final int LEFT = 2;
	static final int RIGHT = 3;

	private int direction;

	public MovementHelper() {
		direction = RIGHT;
	}

	public MovementHelper(int direction) {
		this.direction = direction;
	}

	public int getDirection() {
		return this.direction;
	}
}
