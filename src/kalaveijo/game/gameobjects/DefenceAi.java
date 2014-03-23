package kalaveijo.game.gameobjects;

public class DefenceAi extends Ai {

	public DefenceAi(int currentPosX, int currentPosY, int range, Unit u) {
		super(currentPosX, currentPosY, range, u);
		// TODO Auto-generated constructor stub
	}

	public DefenceAi(Unit u) {
		super(u);
	}

	protected void checkMovement() {
		// leave empty, this motherfucker aint moving see?
	}

	protected void checkAttack() {

	}
}
