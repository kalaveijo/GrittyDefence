package kalaveijo.game.gameobjects;

import kalaveijo.game.engine.Entity;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.engine.template.EntityTemplate;
import kalaveijo.game.util.MapLocation;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/*
 * Handles non unit related player data
 */
public class Player extends Entity {

	private int money = 10;
	private Paint paint;
	private Entity selectedEntity;
	private int machinegunnerPrice = 3;

	public Player(long id, ObjectManager om) {
		super(id, om);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3);
		paint.setTextSize(22);
		selectedEntity = null;
	}

	public void draw(Canvas c) {
		c.drawText("Money: " + money, 500, 35, paint);
	}

	public int getMoney() {
		return this.money;
	}

	/*
	 * attempts to subtract money, returns false if not enough
	 */
	public boolean subtractMoney(int amount) {
		amount = Math.abs(amount);
		int difference = money - amount;
		if (difference > -1) {
			money = money - amount;
			return true;
		}
		return false;
	}

	public void addMoney(int amount) {
		int absoluteAmount = Math.abs(amount);
		money = money + absoluteAmount;
	}

	public void selectEntity(Entity e) {
		this.selectedEntity = null;
		this.selectedEntity = e;
	}

	public Entity getSelectedEntity() {
		return selectedEntity;
	}

	public void removeSelectedEntity() {
		selectedEntity = null;
	}

	public boolean buyUnit(String unitName, MapLocation ml) {

		if (unitName.equals("machinegunner")) {
			if (om.mapLocationIsFree(ml.x, ml.y)) {
				if (subtractMoney(machinegunnerPrice)) {
					Unit u = selectUnitFromTemplates(unitName);
					if (u != null) {
						om.spawnPlayerUnit(u, ml.x, ml.y);
					}
				}
			}
		}

		return false;
	}

	/*
	 * unit ring spawns are here, need to be added manually
	 */
	public Unit selectUnitFromTemplates(String name) {
		for (EntityTemplate et : om.getTemplateManager().getEntityTemplates()) {
			if (et.getName().equals("machinegunner")) {
				Unit u = et.createUnit();
				return u;
				// om.spawnPlayerUnit(u, 4, 4);
			}
		}
		return null;
	}
}
