package kalaveijo.game.gameobjects;

import kalaveijo.game.engine.Entity;
import kalaveijo.game.engine.manager.ObjectManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/*
 * Handles non unit related player data
 */
public class Player extends Entity {

	private int money = 10;
	private Paint paint;

	public Player(long id, ObjectManager om) {
		super(id, om);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3);
		paint.setTextSize(22);
	}

	public void draw(Canvas c) {
		c.drawText("Money: " + money, 500, 35, paint);
	}

	public int getMoney() {
		return this.money;
	}

	/*
	 * attempts to subtract money, returns false if not enough
	 */
	public boolean subtractMoney(int amount) {
		amount = Math.abs(amount);
		if (amount - money > -1) {
			money = money - amount;
			return true;
		}
		return false;
	}

	public void addMoney(int amount) {
		int absoluteAmount = Math.abs(amount);
		money = money + absoluteAmount;
	}
}
