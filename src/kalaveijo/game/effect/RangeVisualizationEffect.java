package kalaveijo.game.effect;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;
import kalaveijo.game.engine.Effect;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.util.MapLocation;
import kalaveijo.game.util.Options;

public class RangeVisualizationEffect extends Effect {

	private int amountOfTicksToLive = 1;
	private ArrayList<MapLocation> tiles;
	
	public RangeVisualizationEffect(MapLocation startLocation, ObjectManager om, ArrayList<MapLocation> tiles) {
		super(startLocation, om);
		this.tiles = tiles;
	}

	public void move(){
		if(amountOfTicksToLive == 0){
			om.addToRemoveList(this);
		}else{
			amountOfTicksToLive--;
		}
	}
	
	public void draw(Canvas c){
		for (MapLocation ml : tiles) {
			c.drawRect(ml.x * Options.TILE_SIZE, ml.y * Options.TILE_SIZE,
					ml.x * Options.TILE_SIZE + Options.TILE_SIZE, ml.y
							* Options.TILE_SIZE + Options.TILE_SIZE,
					new Paint());
		}
	}
	
	
}
