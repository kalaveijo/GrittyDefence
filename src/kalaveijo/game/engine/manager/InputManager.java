package kalaveijo.game.engine.manager;

import java.util.ArrayList;

import kalaveijo.game.engine.GUIElement;
import kalaveijo.game.engine.GUIElementGroup;
import kalaveijo.game.gameobjects.Unit;
import kalaveijo.game.grittydefence.GameSurfaceView;
import kalaveijo.game.util.InputEvent;
import kalaveijo.game.util.MapLocation;
import kalaveijo.game.util.Options;
import android.graphics.Point;
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

			if (lastEvent != null) {

				// check if hits guiElement
				if (getStaticGUIElement(lastEvent) == null) {

					// check if buildphase
					if (gameManager.isBuildPhase()) {
						// if yes

						if (objectManager
								.getEntityByPosition(convertInputToMapLocation(lastEvent)) != null) {
							// move unit

							if (objectManager.getPlayer().getSelectedEntity() == null) {
								// unitring might be open, closi closi
								guiManager.removeLastGUIGroup();
								// if no unit is selected
								objectManager
										.getPlayer()
										.selectEntity(
												objectManager
														.getEntityByPosition(convertInputToMapLocation(lastEvent)));
							}

						} else {
							// create unitring or move selected unit

							if (objectManager.getPlayer().getSelectedEntity() == null) {

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

							} else {
								// if unit is selected, move unit

								Unit u = (Unit) objectManager.getPlayer()
										.getSelectedEntity();

								u.moveUnitToMapLocation(convertInputToMapLocation(lastEvent));
								objectManager.getPlayer()
										.removeSelectedEntity();
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
					// here be haxors and undynamiccode
					GUIElementGroup group = guiManager.getActiveUIGroup();
					if (group != null) {
						if (group.getName().equals("unitRing")) {
							GUIElement element = group
									.findElementByPosition(new Point(
											(int) lastEvent.getEvent().getX(),
											(int) lastEvent.getEvent().getY()));
							if (element.getName().equals("Machinegunner")) {
								MapLocation ml = convertInputToMapLocation(lastEvent);
								// buy machinegunner
								objectManager.getPlayer().buyUnit(
										"machinegunner", ml);
							}
						}
					}
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

		GUIElementGroup group = guiManager.getActiveUIGroup();
		if (group != null) {
			GUIElement element = group.findElementByPosition(new Point(
					(int) iEvent.getEvent().getX(), (int) iEvent.getEvent()
							.getY()));

			return element;
		}

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
