package kalaveijo.game.engine;

import kalaveijo.game.gameobjects.Ai;
import kalaveijo.game.gameobjects.Unit;

//template object that is used to create Units
public class EntityTemplate extends Entity {

	// takes all values needed for unit
	EntityTemplate(ObjectManager om, String name, int health, int speed,
			int range, int atkspeed, Ai ai, String bitmapcontainergroup) {
		super.om = om;
		super.name = name;
		super.health = health;
		super.speed = speed;
		super.range = range;
		super.atkSpeed = atkSpeed;
		super.ai = ai;
		super.bitmapcontainergroup = bitmapcontainergroup;
	}

	public Unit createUnit() {
		return new Unit(om.getNextFreeId(), om);
	}

}
