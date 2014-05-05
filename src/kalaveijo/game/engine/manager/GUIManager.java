package kalaveijo.game.engine.manager;

import java.util.ArrayList;

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

	ArrayList<GUIElementGroup> GUIElementList;

	public GUIManager() {
		GUIElementList = new ArrayList<GUIElementGroup>();
	}

	public void draw(Canvas c) {
		for (GUIElementGroup e : GUIElementList) {
			e.draw(c);
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

	}

	// removes unitring
	public void removeUnitRing() {
		GUIElementGroup removeGroup = null;
		for (GUIElementGroup e : GUIElementList) {
			if (e.getName().equals("unitRing")) {
				removeGroup = e;
			}
		}
		if (removeGroup != null)
			GUIElementList.remove(removeGroup);
	}

}
