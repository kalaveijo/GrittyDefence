package kalaveijo.game.engine;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import kalaveijo.game.grittydefence.GameSurfaceView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.content.res.AssetManager;
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

				String temp = "herp";
			}
		} catch (Exception e) {
			Log.d("XML I/O", e.toString());
		}

	}

	public void loadMap() {

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
