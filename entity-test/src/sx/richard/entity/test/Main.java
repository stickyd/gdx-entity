
package sx.richard.entity.test;

import sx.richard.entity.Component;
import sx.richard.entity.ComponentSystem;
import sx.richard.entity.Engine;
import sx.richard.entity.EngineTask;
import sx.richard.entity.Entity;
import sx.richard.entity.World;
import sx.richard.entity.components.Transform2;
import sx.richard.entity.executors.SortUpdateLayer;
import sx.richard.entity.executors.UpdateComponents;

import com.badlogic.gdx.utils.Array;

public class Main {
	
	private class StareAt extends Component<StareAt> {
		
		private Transform2 transform;
		
		@Override
		public Component<StareAt> copy () {
			return this;
		}
		
		public Transform2 getTarget () {
			return transform;
		}
		
		public void setTarget (Transform2 transform) {
			this.transform = transform;
		}
		
	}
	
	private class StareSystem extends ComponentSystem<StareAt> {
		
		public StareSystem () {
			super(StareAt.class);
		}
		
		@Override
		public void added (StareAt component) {
			System.out.println("Added stare");
		}
		
		@Override
		public void removed (StareAt component) {
			System.out.println("Removed stare");
		}
		
		@Override
		public void update (StareAt component) {
			System.out.println("Update stare...");
			
		}
		
	}
	
	public static void main (String[] args) {
		new Main();
	}
	
	Main () {
		Engine engine = new Engine();
		
		Array<EngineTask> engineTasks = new Array<EngineTask>();
		engineTasks.add(new SortUpdateLayer());
		engineTasks.add(new UpdateComponents());
		engine.setEngineTasks(engineTasks);
		
		World world = new World();
		engine.setWorld(world);
		
		engine.add(new StareSystem());
		
		Entity target = new Entity("target");
		target.add(new Transform2());
		
		Entity viewer = new Entity("viewer");
		StareAt stareAt = new StareAt();
		stareAt.setTarget(target.get(Transform2.class));
		viewer.add(stareAt);
		
		world.add(target);
		world.add(viewer);
		
		for (int i = 0; i < 20; i++) {
			engine.update(1f / 60f);
		}
		
		System.out.println("Complete");
		
	}
	
}
