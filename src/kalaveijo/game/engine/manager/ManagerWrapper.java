package kalaveijo.game.engine.manager;

/*
 * Contains references to all managers
 */
public class ManagerWrapper {

	private GameManager gameManager;
	private GUIManager guiManager;
	private InputManager inputManager;
	private ObjectManager objectManager;
	private TemplateManager templateManager;

	public ManagerWrapper(GameManager gameManager, GUIManager guiManager,
			InputManager inputManager, ObjectManager objectManager,
			TemplateManager templateManager) {
		this.gameManager = gameManager;
		this.guiManager = guiManager;
		this.inputManager = inputManager;
		this.objectManager = objectManager;
		this.templateManager = templateManager;

	}

	public GameManager getGameManager() {
		return gameManager;
	}

	public GUIManager getGuiManager() {
		return guiManager;
	}

	public InputManager getInputManager() {
		return inputManager;
	}

	public ObjectManager getObjectManager() {
		return objectManager;
	}

	public TemplateManager getTemplateManager() {
		return templateManager;
	}

}
