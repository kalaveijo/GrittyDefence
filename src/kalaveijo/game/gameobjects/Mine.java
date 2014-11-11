package kalaveijo.game.gameobjects;

import android.graphics.Point;
import kalaveijo.game.engine.BitmapContainerGroup;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.engine.template.ProjectileTemplate;
import kalaveijo.game.util.MapLocation;

public class Mine extends Unit {

	public Mine(long id, ObjectManager om, String name, int health, int speed,
			int range, int atkSpeed, String bitmapContainerGroup,
			BitmapContainerGroup bcg, ProjectileTemplate projectile) {
		super(id, om, name, health, speed, range, atkSpeed,
				bitmapContainerGroup, bcg, projectile);

	}
	
	public void spawn(Point location, int x, int y) {
		this.location = location;
		this.posX = x;
		this.posY = y;

		// ugly hack for defenceAi param distribution
		if (ai instanceof DefenceAi) {
			((DefenceAi) ai).setTargetLocation(new MapLocation(this.getPosX(),
					this.getPosY()));
		}
	}

}
