package kalaveijo.game.engine.manager;

import java.util.ArrayList;

import kalaveijo.game.grittydefence.GameSurfaceView;
import kalaveijo.game.util.InputEvent;

/*
 * Handles input events
 */
public class InputManager {

	GameManager gameManager;
	GameSurfaceView cv;
	ObjectManager objectManager;
	ArrayList<InputEvent> eventList;

	public InputManager(GameManager gameManager, ObjectManager objectManager,
			GameSurfaceView cv) {
		this.gameManager = gameManager;
		this.cv = cv;
		this.objectManager = objectManager;
		eventList = new ArrayList<InputEvent>();
	}

	public void processInputs() {
		eventList = cv.getInputEvents();
		if (!eventList.isEmpty()) {
			for (InputEvent e : eventList) {

			}
			gameManager.endBuildPhase();
		}

	}
}
