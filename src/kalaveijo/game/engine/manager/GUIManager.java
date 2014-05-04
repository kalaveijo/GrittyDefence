package kalaveijo.game.engine.manager;

import java.util.ArrayList;

import kalaveijo.game.engine.GUIElementGroup;
import android.graphics.Canvas;

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

}
