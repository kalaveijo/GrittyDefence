package kalaveijo.game.engine.manager;

import java.util.ArrayList;

import kalaveijo.game.engine.GUIElement;
import kalaveijo.game.grittydefence.GameSurfaceView;
import kalaveijo.game.util.InputEvent;
import kalaveijo.game.util.MapLocation;
import kalaveijo.game.util.Options;
import android.view.MotionEvent;

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

			// if (guiManager.checkIfInputPointsAtGUI(lastEvent)) {
			// // createUnitRingAtMotionEvent(lastEvent);
			//
			// guiManager.removeLastGUIGroup();
			//
			// } else {
			// guiManager.removeLastGUIGroup();
			// createUnitRingAtMotionEvent(lastEvent);
			// }

			if (lastEvent != null) {

				// check if hits guiElement
				if (getStaticGUIElement(lastEvent) == null) {

					// check if buildphase
					if (gameManager.isBuildPhase()) {
						// if yes

						if (objectManager
								.getEntityByPosition(convertInputToMapLocation(lastEvent)) != null) {
							// move unit
						} else {
							// create unitring

							// check if unitring is already enabled
							if (guiManager
									.findIfLastGUIElementNameEquals("unitRing")) {
								// if enabled, remove
								guiManager.removeLastGUIGroup();
							} else {
								// if not enabled, add
								guiManager.removeLastGUIGroup();
								createUnitRingAtMotionEvent(lastEvent);
							}

						}

					} else {
						// if no
						// check if unit has been clicked
						if (objectManager
								.getEntityByPosition(convertInputToMapLocation(lastEvent)) != null) {
							// do unit ability if has one.

						}
					}

				} else {
					// process which GUI element was hit
				}

			}

		}

	}

	private void createUnitRingAtMotionEvent(InputEvent iEvent) {

		MotionEvent mEvent = iEvent.getEvent();
		int posX = 0;
		int posY = 0;

		boolean xSelected = true;
		boolean ySelected = true;

		for (int x = 0; x < objectManager.getNonArrayListMap().getSizeX(); x++) {
			for (int y = 0; y < objectManager.getNonArrayListMap().getSizeY(); y++) {
				if (x * Options.TILE_SIZE >= mEvent.getX()) {
					if (xSelected) {

						if (x - 1 > -1) {
							posX = x - 1;
							xSelected = false;
						} else {
							posX = x;
							xSelected = false;
						}

					}
				}
				if (y * Options.TILE_SIZE >= mEvent.getY()) {
					if (ySelected) {

						if (y - 1 > -1) {
							posY = y - 1;
							ySelected = false;
						} else {
							posY = y;
							ySelected = false;
						}

					}
				}
			}
		}
		guiManager.createUnitRing(new MapLocation(posX, posY));
	}

	private GUIElement getStaticGUIElement(InputEvent iEvent) {
		return null;
	}

	private MapLocation convertInputToMapLocation(InputEvent iEvent) {

		MotionEvent mEvent = iEvent.getEvent();
		int posX = 0;
		int posY = 0;

		boolean xSelected = true;
		boolean ySelected = true;

		for (int x = 0; x < objectManager.getNonArrayListMap().getSizeX(); x++) {
			for (int y = 0; y < objectManager.getNonArrayListMap().getSizeY(); y++) {
				if (x * Options.TILE_SIZE >= mEvent.getX()) {
					if (xSelected) {

						if (x - 1 > -1) {
							posX = x - 1;
							xSelected = false;
						} else {
							posX = x;
							xSelected = false;
						}

					}
				}
				if (y * Options.TILE_SIZE >= mEvent.getY()) {
					if (ySelected) {

						if (y - 1 > -1) {
							posY = y - 1;
							ySelected = false;
						} else {
							posY = y;
							ySelected = false;
						}

					}
				}
			}
		}
		return new MapLocation(posX, posY);
	}
}
