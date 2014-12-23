package kalaveijo.game.effect;

import android.graphics.Canvas;
import android.graphics.Paint;
import kalaveijo.game.engine.Effect;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.gameobjects.Player;
import kalaveijo.game.util.MapLocation;
import kalaveijo.game.util.Options;

public class SelectionVisualizationEffect extends Effect {

	
	public SelectionVisualizationEffect(MapLocation startLocation,
			ObjectManager om) {
		super(startLocation, om);
		// TODO Auto-generated constructor stub
	}

	public void move(){
		if(om.getPlayer().getSelectedEntity() == null){
			om.addToRemoveList(this);
		}
	}
	
	public void draw(Canvas c){
		
		Paint myPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	    myPaint.setStyle(Paint.Style.STROKE);
		c.drawCircle(startLocation.x * Options.TILE_SIZE + (Options.TILE_SIZE / 2), startLocation.y * Options.TILE_SIZE + (Options.TILE_SIZE / 2), (Options.TILE_SIZE / 2) - 2 , myPaint);
	}
	
}
