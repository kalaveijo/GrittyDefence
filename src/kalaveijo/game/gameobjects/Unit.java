package kalaveijo.game.gameobjects;

import java.util.ArrayList;

import kalaveijo.game.effect.RangeVisualizationEffect;
import kalaveijo.game.engine.BitmapContainerGroup;
import kalaveijo.game.engine.Entity;
import kalaveijo.game.engine.Tickable;
import kalaveijo.game.engine.manager.ObjectManager;
import kalaveijo.game.engine.template.ProjectileTemplate;
import kalaveijo.game.util.MapLocation;
import kalaveijo.game.util.Options;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Unit extends Entity implements Tickable {

	protected ProjectileTemplate projectile;
	// used in cool death
	protected int lastHitDirection = Entity.UP;

	public Unit(long id, ObjectManager om) {
		super(id, om);
		super.health = 10;
		super.speed = 1;
		super.range = 2;
		super.atkSpeed = 1;
		super.status = IDLE;
	}

	public Unit(long id, ObjectManager om, String name, int health, int speed,
			int range, int atkSpeed, String bitmapContainerGroup,
			BitmapContainerGroup bcg, ProjectileTemplate projectile) {
		super(id, om);
		super.name = name;
		super.health = health;
		super.speed = speed;
		super.range = range;
		super.atkSpeed = atkSpeed;
		super.status = IDLE;
		super.bmContainerGroup = bcg;
		this.projectile = projectile;
	}

	public void loadAi(Ai ai) {
		this.ai = ai;
	}

	@Override
	public void loadBitmap(Bitmap m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Canvas c) {
		Bitmap picture = animator.animate();
		// if some reason we cant find correct bitmap
		if (picture == null) {
			if (location != null) {
				Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
				mPaint.setColor(Color.YELLOW);
				c.drawCircle(location.x + offSetX, location.y + offSetY,
						Options.TILE_SIZE / 2, mPaint);
				// offSetX/Y are from superclass
			}
		} else {
			c.drawBitmap(picture, location.x, location.y, new Paint());
			visualizeTargetedTiles(c);
		}
	}

	@Override
	public void move() {
		if (posX != -1) {
			// AI does decision making here
			if (ai != null && health > 0)
				ai.assesAction();
			moveStatus();
		}
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void spawn(Point location, int x, int y) {
		this.location = location;
		this.posX = x;
		this.posY = y;
		
		// ugly hack for defenceAi param distribution
		if (ai instanceof DefenceAi) { 
			((DefenceAi) ai).setTargetLocation(new MapLocation(this.getPosX(), this.getPosY()));
		}	
	}

	/*
	 * handles statemachine and counting
	 */
	protected void moveStatus() {
		if (status != IDLE) {
			switch (status) {

			case ATTACKING:

				// if done attacking
				if (actionLeft - atkSpeed < 0) {
					actionLeft = Options.GAME_SPEED;
					status = IDLE;
				} else {
					actionLeft = actionLeft - atkSpeed;
					// else continnue attacking
				}

				break;

			case MOVING:

				// if done moving
				if (actionLeft - speed < 0) {

					actionLeft = Options.GAME_SPEED;

					posX = nextTileX;
					posY = nextTileY;
					status = IDLE;
				} else {

					currentDirection = calculateDirection(new MapLocation(posX,
							posY), new MapLocation(nextTileX, nextTileY));

					// calculate movement step amount for sprite
					int spriteMoveAmount = Options.TILE_SIZE
							/ (Options.GAME_SPEED / speed);
					// else continnue moving
					actionLeft = actionLeft - speed;

					// if moving to left
					if (nextTileX < posX) {
						location.x = location.x - spriteMoveAmount;
					} else if (nextTileX > posX) { // else right
						location.x = location.x + spriteMoveAmount;
					}

					// if moving up
					if (nextTileY < posY) {
						location.y = location.y - spriteMoveAmount;
					} else if (nextTileY > posY) { // else down
						location.y = location.y + spriteMoveAmount;
					}
				}

				break;

			case DYING:

				if (actionLeft - speed < 0) {
					// die :(
					om.addToDeathList(this);
				} else {
					actionLeft = actionLeft - speed;
					int rand;
					switch (lastHitDirection) {
					case Entity.UP:
						rand = (int) Math.random() * 10;
						if (rand == 0 || rand >= 3)
							rand = 1;
						location.y = location.y - rand;
						break;
					case Entity.UP_LEFT:
						rand = (int) Math.random() * 10;
						if (rand == 0 || rand >= 3)
							rand = 1;
						location.y = location.y - rand;
						location.x = location.x - rand;
						break;
					case Entity.LEFT:
						rand = (int) Math.random() * 10;
						if (rand == 0 || rand >= 3)
							rand = 1;
						location.x = location.x - rand;
						break;
					case Entity.DOWN_LEFT:
						rand = (int) Math.random() * 10;
						if (rand == 0 || rand >= 3)
							rand = 1;
						location.y = location.y + rand;
						location.x = location.x - rand;

						break;
					case Entity.DOWN:
						rand = (int) Math.random() * 10;
						if (rand == 0 || rand >= 3)
							rand = 1;
						location.y = location.y + rand;
						break;
					case Entity.DOWN_RIGHT:
						rand = (int) Math.random() * 10;
						if (rand == 0 || rand >= 3)
							rand = 1;
						location.y = location.y + rand;
						location.x = location.x + rand;
						break;
					case Entity.RIGHT:
						rand = (int) Math.random() * 10;
						if (rand == 0 || rand >= 3)
							rand = 1;
						location.x = location.x + rand;
						break;
					case Entity.UP_RIGHT:
						rand = (int) Math.random() * 10;
						if (rand == 0 || rand >= 3)
							rand = 1;
						location.y = location.y - rand;
						location.x = location.x + rand;
						break;

					}
				}

				break;

			}
		} else {
			// insert sprite into correct place
			super.resetSpritePosition();
		}
	}

	public ProjectileTemplate getProjectile() {
		return projectile;
	}

	// used to check if movement logic works
	public void debugOrder() {
		if (Options.DEBUG) {
			status = MOVING;
			nextTileX = 3;
			nextTileY = 4;
		}
	}

	public void getDamage(int damage) {
		if (health > 0) {
			health = health - damage;
			if (health < 1) {
				actionLeft = Options.GAME_SPEED;
				status = Entity.DYING;
			}
		}
	}

	public void attack(MapLocation ml) {
		// change direction
		this.currentDirection = calculateDirection(new MapLocation(posX, posY),
				ml);

		// shoot projectile
		om.getLiveProjectiles().add(
				projectile.createProjectile(ml, new MapLocation(this.getPosX(),
						this.getPosY()), 1));
	}

	// point target for unit and change status to moving
	public void orderMove(int possibleTargetX, int possibleTargetY) {
		this.setNextTileX(possibleTargetX);
		this.setNextTileY(possibleTargetY);
		this.setStatus(Entity.MOVING);
		currentDirection = calculateDirection(new MapLocation(posX, posY),
				new MapLocation(nextTileX, nextTileY));
	}

	public int getLastHitDirection() {
		return lastHitDirection;
	}

	public void setLastHitDirection(int lastHitDirection) {
		this.lastHitDirection = lastHitDirection;
	}

	public boolean moveUnitToMapLocation(MapLocation ml) {

		if (om.getEntityByPosition(ml) == null) {
			// if position is empty
			this.posX = ml.x;
			this.posY = ml.y;
			
			if (ai instanceof DefenceAi) { 
				((DefenceAi) ai).setTargetLocation(new MapLocation(ml.x, ml.y));
			}
			
			return true;
		}
		return false;

	}

	private void visualizeTargetedTiles(Canvas c) {

		Unit u = (Unit) om.getPlayer().getSelectedEntity();
		// if this unit is selected
		if (u == this) {
			ArrayList<MapLocation> tiles = this.ai.getTilesOnRange(posX, posY,
					range, om);
			// create effect that shows visualization
			om.addToUnderEffectList(new RangeVisualizationEffect(new MapLocation(posX,posY), om,tiles));
		}

	}
}
