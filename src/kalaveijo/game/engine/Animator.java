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
			Bitmap b = findCorrectBitmap(e.getStatus());
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

	private Bitmap findCorrectBitmap(int status) {
		BitmapContainer bc;
		switch (status) {
		case Unit.ATTACKING:
			// todo
			break;
		case Unit.DYING:
			// todo
			break;
		case Unit.IDLE:
			// just randomising some direction
			int i = (int) Math.floor((Math.random() * 3));
			// if going up
			if (i == 0) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.idle_up, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			// if going down
			if (i == 1) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.idle_down, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			// if going left
			if (i == 2) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.idle_left, frame);
				if (bc != null) {
					return bc.getPicture();
				}
			}

			// if going right
			if (i == 3) {
				bc = e.getBmContainerGroup().findBitmapContainerByTypeAndFrame(
						BitmapContainer.idle_right, frame);
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
