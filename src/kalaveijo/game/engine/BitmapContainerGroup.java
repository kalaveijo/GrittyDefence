package kalaveijo.game.engine;

import java.util.ArrayList;

/*
 * Contains sprites for single Entity
 */
public class BitmapContainerGroup {

	private ArrayList<BitmapContainer> group;
	private String name;

	public String getName() {
		return name;
	}

	public BitmapContainerGroup(String name) {
		this.name = name;
		group = new ArrayList<BitmapContainer>();
	}

	public void addBitmapContainer(BitmapContainer bmc) {
		this.group.add(bmc);
	}

	public BitmapContainer findBitmapContainer(String name) {
		for (BitmapContainer bmc : group) {
			if (bmc.getName().equals(name)) {
				return bmc;
			}
		}
		return null;
	}

	public BitmapContainer findBitmapContainerByType(String name, int type) {
		for (BitmapContainer bmc : group) {
			if (bmc.getName().equals(name)) {
				if (bmc.getType() == type) {
					return bmc;
				}
			}
		}
		return null;
	}

	public BitmapContainer findBitmapContainerByFrame(String name, int frame) {
		for (BitmapContainer bmc : group) {
			if (bmc.getName().equals(name)) {
				if (frame == bmc.getFrame()) {
					return bmc;
				}
			}
		}
		return null;
	}
}
