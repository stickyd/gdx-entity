
package sx.richard.entity.editor.actions;

import com.badlogic.gdx.utils.Array;

/** Maintains a stack of actions, all past/previous
 * @author Richard Taylor */
public class EditorActions {
	
	/** An action that occurred, maintains state for an undo
	 * @author Richard Taylor */
	public interface EditorAction {
		
		/** Runs the action */
		public void run ();
		
		/** Undo the action */
		public void undo ();
		
	}
	
	private static final Array<EditorAction> actions;
	private static int index;
	static {
		actions = new Array<EditorAction>();
	}
	
	/** @return whether there is a redo chain available */
	public static boolean canRedo () {
		return actions.size >= index;
	}
	
	/** @return whether there is an undo chain to follow */
	public static boolean canUndo () {
		return index > 0;
	}
	
	/** Redo the next action, if available */
	public static void redo () {
		if (!canRedo())
			throw new IllegalStateException("Cannot redo");
	}
	
	/** Runs an action
	 * @param action the {@link EditorAction} */
	public static void run (EditorAction action) {
		if (canRedo()) {
			for (int i = index; i < actions.size; i++) {
				actions.removeIndex(index);
			}
		}
		actions.add(action);
		index++;
		action.run();
	}
	
	/** Undo the previous action */
	public static void undo () {
		if (!canUndo())
			throw new IllegalStateException("Cannot undo");
		index--;
		actions.get(index).run();
	}
	
}
