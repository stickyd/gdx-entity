
package sx.richard.entity.editor;

import sx.richard.entity.Asset;
import sx.richard.entity.Engine;
import sx.richard.entity.EngineTask;
import sx.richard.entity.Entity;
import sx.richard.entity.Scene2;
import sx.richard.entity.World;
import sx.richard.entity.components.graphics.camera.Camera2;
import sx.richard.entity.components.graphics.gfx2.DrawTexture;
import sx.richard.entity.editor.panel.WorldEditor;
import sx.richard.entity.enginetasks.ClearColor;
import sx.richard.entity.enginetasks.RenderDebugScene;
import sx.richard.entity.enginetasks.RenderScene;
import sx.richard.entity.enginetasks.SortRenderLayer;
import sx.richard.entity.enginetasks.SortUpdateLayer;
import sx.richard.entity.enginetasks.UpdateScene;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;

public class Editor extends ApplicationAdapter {
	
	public static void main (String[] args) {
		new LwjglApplication(new Editor(), "Test", 1280, 720, true);
	}
	
	SpriteBatch batch;
	OrthographicCamera camera;
	ShapeRenderer shapes;
	private Engine engine;
	
	private World world;
	private WorldEditor worldEditor;
	
	@Override
	public void create () {
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
		worldEditor.setSize(500, 300);
		
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.camera.position.x += this.camera.viewportWidth / 2f;
		this.camera.position.y += this.camera.viewportHeight / 2f;
		this.camera.update();
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(this.camera.combined);
		
		shapes = new ShapeRenderer();
		shapes.setProjectionMatrix(this.camera.combined);
		
	}
	
	@Override
	public void render () {
		Gdx.gl20.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		int x = 100;
		int y = Gdx.graphics.getHeight() - 500;
		renderWorldEditor(x, y);
	}
	
	private void renderWorldEditor (float x, float y) {
		Texture texture = worldEditor.render();
		int width = texture.getWidth();
		int height = texture.getHeight();
		batch.begin();
		batch.draw(texture, x, y, width, height);
		batch.end();
		shapes.setColor(0.6f, 0.6f, 0.6f, 1f);
		shapes.begin(ShapeType.Rectangle);
		shapes.rect(x, y, width, height);
		shapes.end();
	}
}
