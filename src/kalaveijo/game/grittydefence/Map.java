package kalaveijo.game.grittydefence;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/*
 * Keeps track of maptiles
 */
public class Map {

	private final int sizeX = 20;
	private final int sizeY = 20;
	private Canvas c;
	private Paint mPaint;
	private MapTile[][] tiles = new MapTile[sizeX][sizeY];
	
	
	public Map(Canvas c, Paint mPaint){
		
		this.c = c;
		this.mPaint = mPaint;
		
		for(int i = 0; i < sizeX; i++){
			for(int e = 0; e < sizeY; e++){
				tiles[i][e] = new MapTile(new Point((i*40),(e*40)), 0, c, mPaint);
			}//for
		}//for
		
	}//Map
	
	public MapTile[][] getTiles(){
		return this.tiles;
	}//getTiles
	
	
	
}
