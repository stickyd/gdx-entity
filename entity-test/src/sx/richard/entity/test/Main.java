
package sx.richard.entity.test;

import sx.richard.entity.Component;
import sx.richard.entity.ComponentAdapter;
import sx.richard.entity.Engine;
import sx.richard.entity.EngineTask;
import sx.richard.entity.Entity;
import sx.richard.entity.Scene2;
import sx.richard.entity.World;
import sx.richard.entity.assets.Asset;
import sx.richard.entity.components.Transform2;
import sx.richard.entity.components.graphics.camera.Camera2;
import sx.richard.entity.components.graphics.gfx2.DrawTexture;
import sx.richard.entity.editor.Editable;
import sx.richard.entity.enginetasks.ClearColor;
import sx.richard.entity.enginetasks.RenderDebugScene;
import sx.richard.entity.enginetasks.RenderScene;
import sx.richard.entity.enginetasks.SortRenderLayer;
import sx.richard.entity.enginetasks.SortUpdateLayer;
import sx.richard.entity.enginetasks.UpdateScene;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class Main extends ApplicationAdapter {
	
	public class StareAt extends ComponentAdapter<StareAt> {
		
		@Editable(name = "other target", description = "This isn't used yet")
		private Transform2 otherTarget;
		@Editable
		private Transform2 target;
		
		@Override
		public Component<StareAt> copy () {
			return this;
		}
		
		@Override
		public Class<?>[] getDependencies () {
			return new Class<?>[] { Transform2.class };
		}
		
		@Override
		public void update (float delta) {
			get(Transform2.class).rotate(-90f * delta);
			get(Transform2.class).getPosition().add(20f * delta, 0);
		}
		
	}
	
	public static void main (String[] args) {
		new LwjglApplication(new Main(), "Test", 320, 320, true);
	}
	
	Engine engine;
	
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
		
		World world = new World();
		
		Scene2 scene = new Scene2();
		engine.setScene(scene);
		scene.setWorld(world);
		
		Entity camera = new Entity("camera");
		Camera2 camera2 = new Camera2();
		camera.add(camera2);
		world.add(camera);
		scene.setCamera(camera2);
		
		Entity viewer = new Entity("viewer");
		StareAt stareAt = new StareAt();
		viewer.add(stareAt);
		world.add(viewer);
		
		Asset arrow = new Asset("arrow.png", Texture.class);
		DrawTexture drawTexture = new DrawTexture(arrow);
		viewer.add(drawTexture);
		
		Entity aaa = new Entity("aa");
		Transform2 t = aaa.get(Transform2.class);
		t.setScale(0.5f, 0.5f);
		t.addPosition(50, 50);
		drawTexture = new DrawTexture(arrow);
		aaa.add(drawTexture);
		viewer.add(aaa);
		
		Engine.debug = true;
		
		// validate the entities
		Array<Entity> invalidEntities = world.getInvalidEntities();
		if (invalidEntities.size > 0) {
			System.out.println("invalid entites! size=" + invalidEntities.size);
			for (Entity entity : invalidEntities) {
				System.out.println("\tinvalid entity=" + entity);
			}
		}
		
	}
	
	@Override
	public void render () {
		engine.run();
	}
}
