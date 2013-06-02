
package sx.richard.entity.editor;

import java.lang.reflect.Field;

import sx.richard.entity.editor.actions.EditorActions.EditorAction;

/** Sets the value from a field
 * @author Richard Taylor */
public class SetValue implements EditorAction {
	
	private final Field field;
	private final Object object;
	private final Object originalValue;
	private final Object value;
	
	/** @param field the {@link Field}
	 * @param object the object
	 * @param value */
	public SetValue (Field field, Object object, Object value) {
		this.field = field;
		this.object = object;
		originalValue = EditableUtils.get(field, object);
		this.value = value;
	}
	
	@Override
	public void run () {
		EditableUtils.set(field, object, value);
	}
	
	@Override
	public void undo () {
		EditableUtils.set(field, object, originalValue);
	}
	
}
