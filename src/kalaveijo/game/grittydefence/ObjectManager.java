package kalaveijo.game.grittydefence;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;

/*
 * Handles all and any game objects
 */
public class ObjectManager {

	private ArrayList<Unit> playerUnit = new ArrayList<Unit>();
	private ArrayList<Unit> enemyUnit = new ArrayList<Unit>();
	private ArrayList<Map> map  = new ArrayList<Map>();
	private Canvas c;
	private Paint mPaint;
	
	public ObjectManager(Canvas c, Paint mPaint){	
		//initialize map
		map.add(new Map(c, mPaint));
	}//Constructor
	
	public ArrayList<Map> getMap(){
		return this.map;
	}//getMap()
	
}//class
