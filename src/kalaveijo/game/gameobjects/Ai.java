package kalaveijo.game.gameobjects;

/*
 * Handles decision making by units
 */
public class Ai {

	protected int targetX, targetY; // movement target
	protected int range;
	protected int currentPosX, currentPosY;
	protected Unit u;

	public Ai(int currentPosX, int currentPosY, int range, Unit u) {
		this.currentPosX = currentPosX;
		this.currentPosY = currentPosY;
		this.targetX = currentPosX;
		this.targetY = currentPosY;
		this.range = range;
		this.u = u;
	}

	/*
	 * called by Unit move, checks what unit needs to do next and orders it
	 */
	public void assesAction() {

	}
}
