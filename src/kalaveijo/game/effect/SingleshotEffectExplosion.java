package kalaveijo.game.effect;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import kalaveijo.game.engine.Entity;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.gameobjects.Projectile;
import kalaveijo.game.gameobjects.Unit;
import kalaveijo.game.util.MapLocation;
import kalaveijo.game.util.Options;

public class SingleshotEffectExplosion extends SingleshotEffect {

	private int offSet = 5;
	private int radius = 1;
	private Projectile projectile;
	private int iX;
	private int iY;
	private boolean runOnce = true;
	
	public SingleshotEffectExplosion(MapLocation startLocation,
			MapLocation endLocation, int health, ObjectManager om, int damage, Projectile projectile) {
		super(startLocation, endLocation, health, om, damage);
		this.projectile = projectile;
	}

	public void draw(Canvas c) {
		if(projectile.getHealth() > offSet){
		c.drawCircle(currentPoint.x, currentPoint.y, 5, new Paint());
		calculateIncrements();
		currentPoint.x = currentPoint.x + iX;
		currentPoint.y = currentPoint.y + iY;
		}else{
			if(runOnce)currentPoint.x = currentPoint.x + (iX*offSet);
			if(runOnce)currentPoint.y = currentPoint.y + (iY*offSet);
			Paint paint = new Paint();
			paint.setColor(Color.YELLOW);
			c.drawCircle(currentPoint.x, currentPoint.y, radius, paint);
			c.drawCircle(currentPoint.x - Options.TILE_SIZE, currentPoint.y, radius, paint); //left
			c.drawCircle(currentPoint.x, currentPoint.y - Options.TILE_SIZE, radius, paint); //above
			c.drawCircle(currentPoint.x + Options.TILE_SIZE, currentPoint.y, radius, paint); //right
			c.drawCircle(currentPoint.x, currentPoint.y + Options.TILE_SIZE, radius, paint); //down
			radius = radius + 5;
			runOnce = false;
		}
	}

	public void causeDamage(int i) {
		// write special conditions how damage is caused
		
		ArrayList<Entity> unitsGettingHit = new ArrayList<Entity>();
		unitsGettingHit.add(om.getEntityByPosition(endLocation));
		unitsGettingHit.add(om.getEntityByPosition(new MapLocation(endLocation.x - 1, endLocation.y))); //left
		unitsGettingHit.add(om.getEntityByPosition(new MapLocation(endLocation.x, endLocation.y - 1))); //above
		unitsGettingHit.add(om.getEntityByPosition(new MapLocation(endLocation.x + 1, endLocation.y))); //right
		unitsGettingHit.add(om.getEntityByPosition(new MapLocation(endLocation.x, endLocation.y + 1))); //down
		
		for(Entity e : unitsGettingHit){
		if (e != null && e instanceof Unit) {
			Unit u = (Unit) e;
			int direction = u.calculateDirection(startLocation, endLocation);
			u.setLastHitDirection(direction);
			u.getDamage(damage);
		}}
	}
	
	// calculate how many steps can be taken
		private void calculateIncrements() {
			iX = (int) Math.floor((endPoint.x - currentPoint.x) / (this.health - offSet));
			iY = (int) Math.floor((endPoint.y - currentPoint.y) / (this.health - offSet));
		}
	
}
