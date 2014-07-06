package kalaveijo.game.engine.manager;

import java.util.ArrayList;

import kalaveijo.game.grittydefence.GameSurfaceView;
import kalaveijo.game.util.InputEvent;
import kalaveijo.game.util.MapLocation;

/*
 * Handles input events
 */
public class InputManager {

	private GameManager gameManager;
	private GameSurfaceView cv;
	private ObjectManager objectManager;
	private ArrayList<InputEvent> eventList;
	private GUIManager guiManager;

	public InputManager(GameManager gameManager, ObjectManager objectManager,
			GameSurfaceView cv, GUIManager guiManager) {
		this.gameManager = gameManager;
		this.cv = cv;
		this.objectManager = objectManager;
		this.guiManager = guiManager;
		eventList = new ArrayList<InputEvent>();
	}

	public void processInputs() {
		eventList = cv.getInputEvents();
		if (!eventList.isEmpty()) {
			InputEvent lastEvent = null;
			for (InputEvent e : eventList) {
				lastEvent = e;
			}
			// gameManager.endBuildPhase();

			guiManager.createUnitRing(new MapLocation(5, 5));
		}

	}
}
