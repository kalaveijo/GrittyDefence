package kalaveijo.game.util;

import android.view.MotionEvent;

/*
 * Utility class that passes user inputs to gamethread for parsing
 */
public class InputEvent {

	MotionEvent event;

	public InputEvent(MotionEvent event) {
		this.event = event;
	}

	public MotionEvent getEvent() {
		return event;
	}

}
