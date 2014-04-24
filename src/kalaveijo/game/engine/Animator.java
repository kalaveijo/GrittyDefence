package kalaveijo.game.engine;

import kalaveijo.game.gameobjects.Unit;
import android.graphics.Bitmap;

/*
 * Handles animating for entity. Keeps track of which frame to show and when
 */
public class Animator {

	private Entity e;
	private int frame = 0;
	private int lastStatus = 0; // 0 is IDLE

	public Animator(Entity e) {
		this.e = e;
	}

	// this is called by entity draw(Canvas c);
	public Bitmap animate() {
		// find unit state
		checkIfStatusChanged();

		if (e.getBmContainerGroup() != null) {
			Bitmap b = findCorrectBitmap();
			if (frame < 3) {
				frame++;
			} else {
				frame = 0;
			}
			return b;
		} else {
			return null;
		}
	}

	// checks if status has changed
	// reset frame if so
	private void checkIfStatusChanged() {
		if (e.getStatus() != lastStatus) {
			lastStatus = e.getStatus();
			frame = 0;
		}
	}

	private Bitmap findCorrectBitmap() {
		BitmapContainer bc;
		switch (e.status) {
		case Unit.ATTACKING:
			// if going up
			if (e.currentDirection == Entity.UP) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.attack_up, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			// if going down
			if (e.currentDirection == Entity.DOWN) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.attack_down, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			// if going left
			if (e.currentDirection == Entity.LEFT) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.attack_left, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			// if going right
			if (e.currentDirection == Entity.RIGHT) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.attack_right, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			// välisuunnat
			if (e.currentDirection == Entity.UP_RIGHT) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.attack_right_up, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			if (e.currentDirection == Entity.DOWN_RIGHT) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.attack_right_down, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			if (e.currentDirection == Entity.UP_LEFT) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.attack_left_up, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			if (e.currentDirection == Entity.DOWN_LEFT) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.attack_left_down, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
					BitmapContainer.attack_right, frame);
			if (bc != null) {
				return bc.getPicture();
			}
			break;
		case Unit.DYING:
			// if going up
			if (e.currentDirection == Entity.UP) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.die_up, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			// if going down
			if (e.currentDirection == Entity.DOWN) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.die_down, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			// if going left
			if (e.currentDirection == Entity.LEFT) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.die_left, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			// if going right
			if (e.currentDirection == Entity.RIGHT) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.die_right, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			// välisuunnat
			if (e.currentDirection == Entity.UP_RIGHT) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.die_right_up, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			if (e.currentDirection == Entity.DOWN_RIGHT) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.die_right_down, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			if (e.currentDirection == Entity.UP_LEFT) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.die_left_up, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			if (e.currentDirection == Entity.DOWN_LEFT) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.die_left_down, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
					BitmapContainer.die_right, frame);
			if (bc != null) {
				return bc.getPicture();
			}
			break;
		case Unit.IDLE:
			// if going up
			if (e.currentDirection == Entity.UP) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.idle_up, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			// if going down
			if (e.currentDirection == Entity.DOWN) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.idle_down, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			// if going left
			if (e.currentDirection == Entity.LEFT) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.idle_left, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			// if going right
			if (e.currentDirection == Entity.RIGHT) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.idle_right, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			// välisuunnat
			if (e.currentDirection == Entity.UP_RIGHT) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.idle_right_up, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			if (e.currentDirection == Entity.DOWN_RIGHT) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.idle_right_down, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			if (e.currentDirection == Entity.UP_LEFT) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.idle_left_up, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			if (e.currentDirection == Entity.DOWN_LEFT) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.idle_left_down, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
					BitmapContainer.idle_right, frame);
			if (bc != null) {
				return bc.getPicture();
			}

			break;
		case Unit.MOVING:
			// if going up
			if (e.getPosY() > e.getNextTileY()) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.move_up, frame);

				if (bc != null) {
					return bc.getPicture();
				}
			}

			// if going down
			if (e.getPosY() < e.getNextTileY()) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.move_down, frame);

				if (bc != null) {
					return bc.getPicture();
				}
			}

			// if going left
			if (e.getPosX() > e.getNextTileX()) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.move_left, frame);

				if (bc != null) {
					return bc.getPicture();
				}
			}

			// if going right
			if (e.getPosX() < e.getNextTileX()) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.move_right, frame);

				if (bc != null) {
					return bc.getPicture();
				}
			}

			break;

		}

		return null;
	}
}
