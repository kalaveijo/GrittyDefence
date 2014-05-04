package kalaveijo.game.engine;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Point;

/*
 * holds all gui elements for single gui "group"/"entity"
 */
public class GUIElementGroup {

	ArrayList<GUIElement> GUIElementList;
	String name;
	boolean isActive = false;

	public GUIElementGroup(String name) {
		GUIElementList = new ArrayList<GUIElement>();
		this.name = name;
	}

	public void draw(Canvas c) {
		for (GUIElement e : GUIElementList) {
			e.draw(c);
		}
	}

	public void addGUIElement(GUIElement e) {
		GUIElementList.add(e);
	}

	/*
	 * int tolerance is how much difference there can be between points
	 */
	public GUIElement findElementByPosition(Point position, int tolerance) {
		for (GUIElement e : GUIElementList) {
			// todo
			return e;
		}
		return null;
	}

}
