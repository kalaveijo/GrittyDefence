package kalaveijo.game.effect;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import kalaveijo.game.engine.BitmapContainer;
import kalaveijo.game.engine.Effect;
import kalaveijo.game.engine.manager.GameManager;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.gameobjects.Unit;
import kalaveijo.game.util.MapLocation;
import kalaveijo.game.util.Options;

public class EnemyPreviewEffect extends Effect {

	GameManager gameManager;
	Unit u;
	int amount;
	public EnemyPreviewEffect(MapLocation startLocation, ObjectManager om, GameManager gm, Unit u, int amount) {
		super(startLocation, om);
		gameManager = gm;
		this.u = u;
		this.amount = amount;
	}
	
	public void draw(Canvas c){
		Paint paint = new Paint();
		Paint borderPaint = new Paint();
		paint.setTextSize(19);
		borderPaint.setTextSize(19);
		borderPaint.setStyle(Style.STROKE);
		borderPaint.setStrokeWidth(1);
		int offset = 5;
		int x = startLocation.x * Options.TILE_SIZE + offset;
		int y = startLocation.y * Options.TILE_SIZE + offset;
		int xd = startLocation.x * Options.TILE_SIZE + offset + Options.TILE_SIZE;
		int yd = startLocation.y * Options.TILE_SIZE + offset + Options.TILE_SIZE + 20;
		
		c.drawRect(x, y, xd, yd, paint);
		c.drawBitmap(u.getBmContainerGroup().findBitmapContainerByType(BitmapContainer.move_left).getPicture(), x, y, paint);
		paint.setColor(Color.WHITE);
		if(Options.DEBUG) c.drawText(u.getName(), x + 3, y + 7, paint);
		c.drawText("" + amount, x + Options.TILE_SIZE/2 - 3, y + Options.TILE_SIZE + 15, paint);
		if(Options.DEBUG) c.drawText(u.getName(), x + 3, y + 7, borderPaint);
	}
	
	public void move(){
		if(!gameManager.isBuildPhase()){
			om.getRemoveList().add(this);
		}
	}

}
