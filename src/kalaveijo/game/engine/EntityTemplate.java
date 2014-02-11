package kalaveijo.game.engine;

import kalaveijo.game.gameobjects.Ai;
import kalaveijo.game.gameobjects.Unit;

//template object that is used to create Units
public class EntityTemplate extends Entity {

	private String player;

	// takes all values needed for unit
	EntityTemplate(ObjectManager om, String name, int health, int speed,
			int range, int atkspeed, String player, String bitmapContainerGroup) {
		super.om = om;
		super.name = name;
		super.health = health;
		super.speed = speed;
		super.range = range;
		super.atkSpeed = atkSpeed;
		this.player = player;
		super.bitmapContainerGroup = bitmapContainerGroup;
	}

	public Unit createUnit() {
		Unit u = new Unit(super.om.getNextFreeId(), super.om, super.name,
				super.health, super.speed, super.range, super.atkSpeed,
				super.bitmapContainerGroup);
		Ai ai = null;
		if (player.equals("player")) {
			ai = new Ai(u); // actually needs player Ai implementation
		} else if (player.equals("enemy")) {
			ai = new Ai(u); // actually needs player Ai implementation
		}
		return u;
	}
}
