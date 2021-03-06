package kalaveijo.game.engine.template;

import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.gameobjects.Projectile;
import kalaveijo.game.util.MapLocation;

public class ProjectileTemplate extends Projectile {

	public ProjectileTemplate(long id, ObjectManager om, int health,
			String bitmapContainerGroup, String name, String effect, int damage) {
		super(id, om, null, null, health, effect, damage);
		super.name = name;
		super.bitmapContainerGroup = bitmapContainerGroup;
		super.effect = effect;
	}

	public Projectile createProjectile(MapLocation targetLocation,
			MapLocation currentLocation, int damage) {
		return new Projectile(super.om.getNextFreeId(), super.om,
				targetLocation, currentLocation, super.health, super.effect,
				damage);
	}
}
