package kalaveijo.game.engine.template;

import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.gameobjects.Projectile;
import kalaveijo.game.util.MapLocation;

public class ProjectileTemplate extends Projectile {

	public ProjectileTemplate(int id, ObjectManager om, int health,
			String bitmapContainerGroup, String name, String effect) {
		super(id, om, null, null, health, effect, 0);
		super.name = name;
		super.bitmapContainerGroup = bitmapContainerGroup;
	}

	public Projectile createProjectile(MapLocation targetLocation,
			MapLocation currentLocation, int damage) {
		return new Projectile(super.om.getNextFreeId(), super.om,
				targetLocation, currentLocation, super.health, super.effect,
				damage);
	}
}
