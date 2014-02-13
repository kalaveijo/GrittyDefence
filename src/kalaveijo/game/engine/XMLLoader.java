package kalaveijo.game.engine;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import kalaveijo.game.gameobjects.Map;
import kalaveijo.game.gameobjects.MapTile;
import kalaveijo.game.gameobjects.Mission;
import kalaveijo.game.gameobjects.MissionWave;
import kalaveijo.game.gameobjects.MovementHelper;
import kalaveijo.game.gameobjects.SpawnTile;
import kalaveijo.game.grittydefence.GameSurfaceView;
import kalaveijo.game.util.Options;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.content.res.AssetManager;
import android.graphics.Point;
import android.util.Log;

/*
 * Generic class that will load and parse xml into entities
 * Courtesy of http://theopentutorials.com/tutorials/android/xml/android-simple-xml-dom-parser/
 */
public class XMLLoader {

	private ObjectManager om;
	private GameSurfaceView cv;

	public XMLLoader(ObjectManager om, GameSurfaceView cv) {
		this.om = om;
		this.cv = cv;
	}

	// reads all entities from xml and creates templates from them
	public void loadEntities() {
		try {
			AssetManager assetManager = cv.getContext().getAssets();
			Document entitylist = readXml(assetManager.open("unitlist.xml"));
			NodeList nodeList = entitylist.getElementsByTagName("unit");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Element e = (Element) nodeList.item(i);
				String name = getValue(e, "name");
				int health = Integer.parseInt(getValue(e, "health"));
				int speed = Integer.parseInt(getValue(e, "speed"));
				int range = Integer.parseInt(getValue(e, "range"));
				int atkspeed = Integer.parseInt(getValue(e, "atkspeed"));
				String player = getValue(e, "player");
				String bitmapcontainergroup = getValue(e,
						"bitmapcontainergroup");
				om.addEntityTemplate(new EntityTemplate(om, name, health,
						speed, range, atkspeed, player, bitmapcontainergroup));
			}
		} catch (Exception e) {
			Log.d("XML I/O", e.toString());
		}

	}

	public void loadMap() {
		try {

			Map map;
			MapTile[][] tiles;
			MovementHelper[][] helpers;
			ArrayList<SpawnTile> spawners = new ArrayList<SpawnTile>();
			int x;
			int y;
			String name;

			AssetManager assetManager = cv.getContext().getAssets();
			Document entitylist = readXml(assetManager.open("maplist.xml"));
			NodeList nodeList = entitylist.getElementsByTagName("map");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Element e = (Element) nodeList.item(i);
				x = Integer.parseInt(getValue(e, "x"));
				y = Integer.parseInt(getValue(e, "y"));
				name = getValue(e, "name");

				// find tiles
				tiles = new MapTile[x][y];
				int tx = 0;
				int ty = 0;
				nodeList = entitylist.getElementsByTagName("tile");
				for (i = 0; i < nodeList.getLength(); i++) {
					e = (Element) nodeList.item(i);
					tiles[tx][ty] = new MapTile(om.getNextFreeId(), om,
							new Point(tx * Options.TILE_SIZE, ty
									* Options.TILE_SIZE),
							Integer.parseInt(getValue(e, "type")));

					tx++;
					if (tx >= x) {
						tx = 0;
						ty++;
					}
				}

				// find movement helpers
				helpers = new MovementHelper[x][y];
				tx = 0;
				ty = 0;
				nodeList = entitylist.getElementsByTagName("helper");
				for (i = 0; i < nodeList.getLength(); i++) {
					e = (Element) nodeList.item(i);

					helpers[tx][ty] = new MovementHelper(om.getNextFreeId(),
							om, MovementHelper.parseDirection(getValue(e,
									"direction")),
							new Point(tx * Options.TILE_SIZE, ty
									* Options.TILE_SIZE));

					tx++;
					if (tx >= x) {
						tx = 0;
						ty++;
					}
				}

				// find movement helpers
				spawners = new ArrayList<SpawnTile>();
				nodeList = entitylist.getElementsByTagName("spawn");

				for (i = 0; i < nodeList.getLength(); i++) {
					e = (Element) nodeList.item(i);
					spawners.add(new SpawnTile(om.getNextFreeId(), om, Integer
							.parseInt(getValue(e, "x")), Integer
							.parseInt(getValue(e, "y"))));
				}

				map = new Map(om.getNextFreeId(), name, om, x, y, tiles,
						helpers, spawners);

				om.addMap(map);
			}
		} catch (Exception e) {
			Log.d("XML I/O", e.toString());
		}
	}

	// loads all missions and associated waves
	public void loadMissions() {
		try {

			Mission mission;
			String name;
			String map;
			String[] waves;

			AssetManager assetManager = cv.getContext().getAssets();
			Document entitylist = readXml(assetManager.open("missionlist.xml"));
			NodeList nodeList = entitylist.getElementsByTagName("mission");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Element e = (Element) nodeList.item(i);

				// store mission name

				// store map name

				// loop all waves into String[]

				// loadWaves(String[])
			}

		} catch (Exception e) {
			Log.d("MissionLoadError", e.toString());
		}
	}

	// loads waves, each array pointer has wave name
	public ArrayList<MissionWave> loadWaves(String[] waves) {
		ArrayList<MissionWave> waveList = new ArrayList<MissionWave>();

		// open wavelist.xml

		// find correct wave information by name

		// store wave into arraylist

		return waveList;
	}

	// reads inputstream and outputs document
	// inputstream will be given by AssetManager
	private Document readXml(InputStream inputStream) {// desired fps
		Document document = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = factory.newDocumentBuilder();
			InputSource inputSource = new InputSource(inputStream);
			document = db.parse(inputSource);
		} catch (Exception e) {
			Log.d("XML Parser Error", e.getMessage());
			return null;
		}
		return document;
	}

	private String getValue(Element item, String name) {
		NodeList nodes = item.getElementsByTagName(name);
		return this.getTextNodeValue(nodes.item(0));
	}

	private final String getTextNodeValue(Node node) {
		Node child;
		if (node != null) {
			if (node.hasChildNodes()) {
				child = node.getFirstChild();
				while (child != null) {
					if (child.getNodeType() == Node.TEXT_NODE) {
						return child.getNodeValue();
					}
					child = child.getNextSibling();
				}
			}
		}
		return "";
	}
}
