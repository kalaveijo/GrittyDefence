package kalaveijo.game.gameobjects;

import java.util.ArrayList;

import kalaveijo.game.effect.SelectionVisualizationEffect;
import kalaveijo.game.engine.Entity;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.engine.template.EntityTemplate;
import kalaveijo.game.util.MapLocation;
import kalaveijo.game.util.Methods;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/*
 * Handles non unit related player data
 */
public class Player extends Entity {

	private int money = 8;
	private Paint paint;
	private Entity selectedEntity;
	private int machinegunnerPrice = 3;
	private int wallPrice = 1;
	private int minePrice = 2;
	private int mortarPrice = 6;
	private ArrayList<String> selectExcludeList;
	private ArrayList<String> selectExcludeListDuringWave;

	public Player(long id, ObjectManager om) {
		super(id, om);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(30);
		selectedEntity = null;
		//selectExcludeList is a hack, in reality objects should have property where selection is ignored
		//will be corrected in KiloVictor v2
		selectExcludeList = new ArrayList<String>();
		selectExcludeListDuringWave = new ArrayList<String>();
		selectExcludeList.add("hq");
		selectExcludeListDuringWave.add("hq");
		selectExcludeListDuringWave.add("mine");
		selectExcludeListDuringWave.add("wall");
	}

	public void draw(Canvas c) {
		Paint border = new Paint();
		border.setColor(Color.BLACK);
		border.setStyle(Paint.Style.STROKE);
		border.setStrokeWidth(1);
		border.setTextSize(30);
		c.drawText("Money: " + money, 500, 35, paint);
		c.drawText("Money: " + money, 500, 35, border);
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
		if(!Methods.ifStringExistInList(e.getName(), selectExcludeList)){
			this.selectedEntity = e;
			om.addToAboveEffectList(new SelectionVisualizationEffect(new MapLocation(e.getPosX(),e.getPosY()), om));
		}	
	}
	
	public void selectEntity(Entity e, boolean isBuildPhase) {
		this.selectedEntity = null;
		if(isBuildPhase){
			if(!Methods.ifStringExistInList(e.getName(), selectExcludeList)){
				this.selectedEntity = e;
			}	
		}else{
			if(!Methods.ifStringExistInList(e.getName(), selectExcludeListDuringWave)){
				this.selectedEntity = e;
			}
		}
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
		if (unitName.equals("wall")) {
			if (om.mapLocationIsFree(ml.x, ml.y)) {
				if (subtractMoney(wallPrice)) {
					Unit u = selectUnitFromTemplates(unitName);
					if (u != null) {
						om.spawnPlayerUnit(u, ml.x, ml.y);
					}
				}
			}
		}

		if (unitName.equals("mine")) {
			if (om.mapLocationIsFreeOfMines(ml)) {
				if (om.mapLocationIsFree(ml.x, ml.y)) {
					if (subtractMoney(minePrice)) {
						Unit u = selectUnitFromTemplates(unitName);
						if (u != null) {
							om.spawnPlayerUnit(u, ml.x, ml.y);
						}
					}
				}
			}
		}
		
		if (unitName.equals("mortar")) {
			if (om.mapLocationIsFreeOfMines(ml)) {
				if (om.mapLocationIsFree(ml.x, ml.y)) {
					if (subtractMoney(mortarPrice)) {
						Unit u = selectUnitFromTemplates(unitName);
						if (u != null) {
							om.spawnPlayerUnit(u, ml.x, ml.y);
						}
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
			if (et.getName().equals(name)) {
				Unit u = et.createUnit();
				return u;
				// om.spawnPlayerUnit(u, 4, 4);
			}
			// if (et.getName().equals("wall")) {
			// Unit u = et.createUnit();
			// return u;
			// // om.spawnPlayerUnit(u, 4, 4);
			// }
		}
		return null;
	}
}
