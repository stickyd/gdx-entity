
package sx.richard.entity.test;

import sx.richard.entity.Component;
import sx.richard.entity.ComponentAdapter;
import sx.richard.entity.Engine;
import sx.richard.entity.EngineTask;
import sx.richard.entity.Entity;
import sx.richard.entity.World;
import sx.richard.entity.components.Transform2;
import sx.richard.entity.components.graphics.core.ClearColor;
import sx.richard.entity.editor.Editable;
import sx.richard.entity.executors.RenderComponents;
import sx.richard.entity.executors.SortRenderLayer;
import sx.richard.entity.executors.SortUpdateLayer;
import sx.richard.entity.executors.UpdateComponents;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
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
		public void update (float delta) {}
		
	}
	
	public static void main (String[] args) {
		new LwjglApplication(new Main(), "Test", 640, 640, true);
	}
	
	Engine engine;
	
	@Override
	public void create () {
		engine = new Engine();
		
		Array<EngineTask> engineTasks = new Array<EngineTask>();
		engineTasks.add(new SortUpdateLayer());
		engineTasks.add(new UpdateComponents());
		engineTasks.add(new SortRenderLayer());
		engineTasks.add(new RenderComponents());
		engine.setEngineTasks(engineTasks);
		
		World world = new World();
		engine.setWorld(world);
		
		Entity clear = new Entity("clear");
		clear.add(new ClearColor());
		world.add(clear);
		
		Entity target = new Entity("target");
		target.add(new Transform2());
		
		Entity viewer = new Entity("viewer");
		StareAt stareAt = new StareAt();
		stareAt.target = target.get(Transform2.class);
		viewer.add(new Transform2());
		viewer.add(stareAt);
		
		world.add(target);
		world.add(viewer);
		
		// validate the entities
		
	}
	
	@Override
	public void render () {
		engine.run();
	}
}
