package kalaveijo.game.engine.manager;

import kalaveijo.game.engine.GUIElement;
import kalaveijo.game.engine.GUIElementGroup;
import kalaveijo.game.util.MapLocation;
import kalaveijo.game.util.Options;
import android.graphics.Canvas;
import android.graphics.Point;

/*
 * keeps track which gui elements are active and handles switching between elements based on input
 */
public class GUIManager {

	private GUIElementGroup[] GUIElementList;

	public GUIManager() {
		GUIElementList = new GUIElementGroup[Options.MAX_GUI_LAYERS];
	}

	public void draw(Canvas c) {
		if (GUIElementList.length != 0) {
			if (GUIElementList[GUIElementList.length - 1] != null) {
				GUIElementList[GUIElementList.length - 1].draw(c);
			}
		}
	}

	// creates unit ring at maplocation
	public void createUnitRing(MapLocation ml) {

		Point center = new Point(ml.x * Options.TILE_SIZE
				+ (Options.TILE_SIZE / 2), ml.y * Options.TILE_SIZE
				+ (Options.TILE_SIZE / 2));

		// create unitRing group
		GUIElementGroup unitRing = new GUIElementGroup("unitRing");

		// add units
		// to the right of center
		unitRing.addGUIElement(new GUIElement(null, "Machinegunner", new Point(
				center.x + 40, center.y)));

		addGUIGroup(unitRing);

	}

	// // removes unitring
	// public void removeUnitRing() {
	// GUIElementGroup removeGroup = null;
	// for (GUIElementGroup e : GUIElementList) {
	// if (e.getName().equals("unitRing")) {
	// removeGroup = e;
	// }
	// }
	// if (removeGroup != null)
	// GUIElementList.remove(removeGroup);
	// }

	public void removeLastGUIGroup() {
		if (GUIElementList.length != 0) {
			GUIElementList[GUIElementList.length - 1] = null;
		}
	}

	public void addGUIGroup(GUIElementGroup newGroup) {

		if (GUIElementList.length < Options.MAX_GUI_LAYERS) {
			GUIElementList[GUIElementList.length] = newGroup;
		}
		// fail silently, good coding ja?
	}

	public GUIElementGroup getActiveUIGroup() {
		// if (GUIElementList.length != 0) {
		return GUIElementList[GUIElementList.length - 1];
		// }
		// return null;
	}
}
