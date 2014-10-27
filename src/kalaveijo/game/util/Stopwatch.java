package kalaveijo.game.util;

/*
 * Class that can measure time and tell when certain amount has been achieved
 */
public class Stopwatch {

	private long milliSecondsToCount = 0;
	private long startTime = 0;

	public Stopwatch(int milliSecondsToCount) {
		this.milliSecondsToCount = milliSecondsToCount;
	}

	public void setHowManyMillisecondsToCount(int milliSecondsToCount) {
		this.milliSecondsToCount = milliSecondsToCount;
	}

	public void start() {
		this.startTime = System.currentTimeMillis();
	}

	public long howManyMillisecondsHasPassed() {
		if (startTime > 0) {
			return (long) System.currentTimeMillis() - (long) startTime;
		} else {
			return 0;
		}
	}

	public boolean hasEnoughTimePassed() {
		if (startTime > 0) {
			if (milliSecondsToCount < System.currentTimeMillis() - startTime) {
				return true;
			}
		}
		return false;

	}
}
