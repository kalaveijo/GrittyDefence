package kalaveijo.game.engine;

import kalaveijo.game.gameobjects.Unit;
import kalaveijo.game.util.Options;
import android.graphics.Bitmap;

/*
 * Handles animating for entity. Keeps track of which frame to show and when
 */
public class Animator {

	private Entity e;
	private int frame = 0;
	private int lastStatus = 0; // 0 is IDLE
	private boolean skipFrame = false;
	private int modifier = -1;
	private int howManyFramesToSkip = 2;
	private int framesSkipped = 0;
	private boolean allowFrameChange = true;
	private int maxActionLeft = Options.GAME_SPEED;

	public Animator(Entity e) {
		this.e = e;
		
	}

	// this is called by entity draw(Canvas c);
	public Bitmap animate() {
		// find unit state
		checkIfStatusChanged();
		
		//if dying, animate differently
		if(e.getStatus() == Entity.DYING) return animateDying();
		
		// move frame if should
		if(framesSkipped >= howManyFramesToSkip){							
				if (frame == 3 || frame == 0) {
					
					modifier = modifier * -1;
					frame = frame + modifier;
				} else {
					frame = frame + modifier;
				}
				allowFrameChange = false;
				framesSkipped = 0;		
		}else{
			framesSkipped++;
		}
		
		// fetch correct bitmap
		if (e.getBmContainerGroup() != null) {
			Bitmap b = findCorrectBitmap();
			return b;
		} else {
			return null;
		}		
	}

	
	private Bitmap animateDying(){
		
		int ActionLeft = e.getActionLeft();
		int interval = maxActionLeft / 4;
		
		// move frame if should
				if(framesSkipped >= howManyFramesToSkip){	
					
						if(ActionLeft > interval * 3){
							frame = 0;
						}else if(ActionLeft > interval * 2){
							frame = 1;
						}else if(ActionLeft > interval){
							frame = 2;
						}else{
							frame = 3;
						}
						
						allowFrameChange = false;
						framesSkipped = 0;		
				}else{
					framesSkipped++;
				}
				
				// fetch correct bitmap
				if (e.getBmContainerGroup() != null) {
					Bitmap b = findCorrectBitmap();
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
			modifier = -1;
			
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
