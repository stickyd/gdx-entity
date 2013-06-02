
package sx.richard.entity.editor;

import sx.richard.entity.Asset;
import sx.richard.entity.Engine;
import sx.richard.entity.EngineTask;
import sx.richard.entity.Entity;
import sx.richard.entity.Scene2;
import sx.richard.entity.World;
import sx.richard.entity.components.graphics.camera.Camera2;
import sx.richard.entity.components.graphics.gfx2.DrawTexture;
import sx.richard.entity.editor.panel.ComponentList;
import sx.richard.entity.editor.panel.EntityList;
import sx.richard.entity.editor.panel.GamePreview;
import sx.richard.entity.editor.panel.WorldEditor;
import sx.richard.entity.enginetasks.ClearColor;
import sx.richard.entity.enginetasks.RenderDebugScene;
import sx.richard.entity.enginetasks.RenderScene;
import sx.richard.entity.enginetasks.SortRenderLayer;
import sx.richard.entity.enginetasks.SortUpdateLayer;
import sx.richard.entity.enginetasks.UpdateScene;
import sx.richard.eventbus.EventBus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

public class Editor extends ApplicationAdapter {
	
	public static EventBus events;
	
	static {
		events = new EventBus();
	}
	
	public static void main (String[] args) {
		new LwjglApplication(new Editor(), "Test", 1280, 720, true);
	}
	
	SpriteBatch batch;
	OrthographicCamera camera;
	Engine engine;
	Table root;
	ShapeRenderer shapes;
	Stage stage;
	World world;
	WorldEditor worldEditor;
	
	@Override
	public void create () {
		
		Assets.load();
		
		engine = new Engine();
		
		Array<EngineTask> engineTasks = new Array<EngineTask>();
		engineTasks.add(new ClearColor());
		engineTasks.add(new SortUpdateLayer());
		engineTasks.add(new UpdateScene());
		engineTasks.add(new SortRenderLayer());
		engineTasks.add(new RenderScene());
		engineTasks.add(new RenderDebugScene());
		engine.setEngineTasks(engineTasks);
		
		world = new World();
		
		Scene2 scene = new Scene2();
		engine.setScene(scene);
		scene.setWorld(world);
		
		Entity camera = new Entity("camera");
		Camera2 camera2 = new Camera2();
		camera.add(camera2);
		world.add(camera);
		scene.setCamera(camera2);
		
		Entity arrow = new Entity("arrow");
		Asset asset = new Asset("arrow.png", Texture.class);
		arrow.add(new DrawTexture(asset));
		world.add(arrow);
		
		// validate the entities
		Array<Entity> invalidEntities = world.getInvalidEntities();
		if (invalidEntities.size > 0) {
			System.out.println("invalid entites! size=" + invalidEntities.size);
			for (Entity entity : invalidEntities) {
				System.out.println("\tinvalid entity=" + entity);
			}
		}
		
		worldEditor = new WorldEditor(world);
		
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		
		this.camera = (OrthographicCamera)stage.getCamera();
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(this.camera.combined);
		
		shapes = new ShapeRenderer();
		shapes.setProjectionMatrix(this.camera.combined);
		
		root = new Table();
		stage.addActor(root);
		
		final GamePreview www = new GamePreview(world);
		
		root.add(new Table() {
			
			{
				add(worldEditor).expand().fill().space(5);
				row();
				add(www).expand().fill().space(5);
			}
		}).expand().fill().padLeft(5).padTop(5).padBottom(5).space(5);
		
		root.add(new Table() {
			
			{
				add(new EntityList(world)).expand().fill();
			}
		}).width(180).fill().expandY().padTop(5).space(5).padBottom(5);
		
		root.add(new Table() {
			
			{
				add(new ComponentList()).expand().fill();
			}
		}).width(300).fill().expandY().padTop(5).space(5).padRight(5).padBottom(5);
		
		Gdx.input.setInputProcessor(stage);
		
	}
	
	@Override
	public void render () {
		Gdx.gl20.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}
	
	@Override
	public void resize (int w, int h) {
		stage.setViewport(w, h, false);
		root.setSize(w, h);
		root.invalidate();
		camera.viewportWidth = w;
		camera.viewportHeight = h;
		camera.update();
	}
	
}
