package sx.richard.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;

/**
 * A basic {@link AbstractEngine} implementation, keeps the systems in an
 * {@link OrderedMap}
 * 
 * @author Richard Taylor
 */
public final class Engine implements EntityGroupListener {

	private static AssetManager assetManager;
	/** Whether this is debug mode or not */
	public static boolean debug;

	/** @return the {@link AssetManager} */
	public static AssetManager getAssetManager() {
		return assetManager;
	}

	private final AssetCollection assets;
	private final Array<EngineTask> engineTasks;
	private final Render render;
	private Scene<?> scene;

	{
		SpriteBatch spriteBatch = new SpriteBatch();
		render = new Render(spriteBatch);
		engineTasks = new Array<EngineTask>();
		assets = new AssetCollection();
		assetManager = new AssetManager();
		Gdx.input.setInputProcessor(Input.getInputProcessor());
	}

	// These warnings are safe
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void entityAdded(EntityGroup group, Entity entity) {
		for (int i = 0, n = entity.getComponentCount(); i < n; i++) {
			Class componentType = entity.get(i);
			Component<?> component = entity.get(componentType);
			component.setGroup(group);
			component.added();
		}
	}

	// These warnings are safe
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void entityRemoved(EntityGroup group, Entity entity) {
		for (int i = 0, n = entity.getComponentCount(); i < n; i++) {
			Class componentType = entity.get(i);
			Component<?> component = entity.get(componentType);
			component.setGroup(null);
			component.removed();
		}
	}

	/** @return the {@link AssetCollection} */
	public AssetCollection getAssets() {
		return assets;
	}

	/** @return the {@link EngineTask}s */
	public Array<EngineTask> getEngineTasks() {
		return new Array<EngineTask>(engineTasks);
	}

	/** @return the {@link Render} object */
	public Render getRender() {
		return render;
	}

	/** @return the {@link Scene} */
	public Scene<?> getScene() {
		return scene;
	}

	/** Runs the {@link EngineTask}s */
	public void run() {
		for (EngineTask engineTask : engineTasks) {
			engineTask.execute(this);
		}
		Input.update();
	}

	/**
	 * Sets the engine tasks
	 * 
	 * @param engineTasks
	 *            the engine tasks
	 */
	public void setEngineTasks(Array<EngineTask> engineTasks) {
		this.engineTasks.clear();
		this.engineTasks.addAll(engineTasks);
	}

	/**
	 * @param scene
	 *            the {@link Scene} to set, must not be <code>null</code>
	 */
	public void setScene(Scene<?> scene) {
		if (scene == null)
			throw new NullPointerException("Scene must not be null");
		this.scene = scene;
	}

}
