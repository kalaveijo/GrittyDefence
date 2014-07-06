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
		if (findGUIItemAmount() != 0) {
			if (GUIElementList[findGUIItemAmount() - 1] != null) {
				GUIElementList[findGUIItemAmount() - 1].draw(c);
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
		if (findGUIItemAmount() != 0) {
			GUIElementList[findGUIItemAmount() - 1] = null;
		}
	}

	public void addGUIGroup(GUIElementGroup newGroup) {
		if (findGUIItemAmount() < Options.MAX_GUI_LAYERS) {
			GUIElementList[findGUIItemAmount()] = newGroup;
		}
		// fail silently, good coding ja?
	}

	private int findGUIItemAmount() {
		for (int i = 0; i < Options.MAX_GUI_LAYERS; i++) {
			if (GUIElementList[i] == null) {
				return i;
			}
		}
		return 0;
	}

	public GUIElementGroup getActiveUIGroup() {
		// if (GUIElementList.length != 0) {
		return GUIElementList[findGUIItemAmount() - 1];
		// }
		// return null;
	}
}
