package kalaveijo.game.engine.template;

import java.util.ArrayList;

import kalaveijo.game.engine.Entity;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.engine.manager.TemplateManager;
import kalaveijo.game.gameobjects.Ai;
import kalaveijo.game.gameobjects.DefenceAi;
import kalaveijo.game.gameobjects.Unit;

//template object that is used to create Units
public class EntityTemplate extends Entity {

	private String player;
	private TemplateManager tm;
	private String projectile;

	// takes all values needed for unit
	public EntityTemplate(ObjectManager om, String name, int health, int speed,
			int range, int atkspeed, String player,
			String bitmapContainerGroup, TemplateManager tm, String projectile) {
		super.om = om;
		super.name = name;
		super.health = health;
		super.speed = speed;
		super.range = range;
		super.atkSpeed = atkSpeed;
		this.player = player;
		super.bitmapContainerGroup = bitmapContainerGroup;
		this.tm = tm;
		this.projectile = projectile;
	}

	public Unit createUnit() {
		Unit u = null;
		if (tm.getProjTemplates() != null) {
			u = new Unit(super.om.getNextFreeId(), super.om, super.name,
					super.health, super.speed, super.range, super.atkSpeed,
					super.bitmapContainerGroup, super.getBmContainerGroup(),
					pairProjectileTemplate());
		} else {
			u = new Unit(super.om.getNextFreeId(), super.om, super.name,
					super.health, super.speed, super.range, super.atkSpeed,
					super.bitmapContainerGroup, super.getBmContainerGroup(),
					null);
		}

		Ai ai = null;
		if (player.equals("player")) {
			ai = new DefenceAi(u);
		} else if (player.equals("enemy")) {
			ai = new Ai(u);
		}
		u.loadAi(ai);
		return u;
	}

	private ProjectileTemplate pairProjectileTemplate() {
		ProjectileTemplate projTemp = null;
		ArrayList<ProjectileTemplate> projList = tm.getProjTemplates();

		for (ProjectileTemplate pt : projList) {
			if (pt.getName().equals(this.projectile)) {
				projTemp = pt;
			}
		}

		return projTemp;
	}
}
