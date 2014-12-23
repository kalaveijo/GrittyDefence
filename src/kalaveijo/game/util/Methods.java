package kalaveijo.game.util;

import java.util.ArrayList;

import kalaveijo.game.engine.BitmapContainer;
import kalaveijo.game.engine.Entity;

/*
 * Contains handy methods
 */
abstract public class Methods {

	// resizes array and appends object
	static public Object[] appendAndResizeArray(Object[] arrayToResize,
			Object objectToAppend) {
		Object[] b = new Object[arrayToResize.length + 1];

		for (int i = 0; i < arrayToResize.length; i++) {
			if (arrayToResize[i] != null) {
				b[i] = arrayToResize[i];
			} else {
				b[i] = objectToAppend;
			}

		}
		return b;
	}

	static public int convertDirectionToBitmapTypeDie(int type) {
		switch (type) {
		case Entity.UP:
			return BitmapContainer.die_up;
		case Entity.UP_RIGHT:
			return BitmapContainer.die_right_up;
		case Entity.RIGHT:
			return BitmapContainer.die_right;
		case Entity.DOWN_RIGHT:
			return BitmapContainer.die_right_down;
		case Entity.DOWN:
			return BitmapContainer.die_down;
		case Entity.DOWN_LEFT:
			return BitmapContainer.die_left_down;
		case Entity.LEFT:
			return BitmapContainer.die_left;
		case Entity.UP_LEFT:
			return BitmapContainer.die_left_up;
		}
		return BitmapContainer.die_left;
	}

	static public boolean ifStringExistInList(String key, ArrayList<String> list){
		
		for(String string : list){
			if(key.equals(string)){
				return true;
			}
		}
		
		return false;
	}
	
	static public boolean ifListsHaveCommonString(ArrayList<String> list, ArrayList<String> list2){
		
		for(String s : list){
			for(String s2 : list2){
				if(s.equals(s2)){
					return true;
				}
			}
		}
		
		return false;
	}
}
