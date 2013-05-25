
package sx.richard.entity.editor;

import java.lang.reflect.Field;

import com.badlogic.gdx.utils.ObjectMap;

/** Enables the editing of a particular field; this also holds the static
 * collection of {@link FieldEditor}s. The {@link FieldEditor#toString()} method
 * should return a human-readible value for the editor.
 * @author Richard Taylor */
public abstract class FieldEditor<T extends Object> {
	
	private static final ObjectMap<Class<?>, FieldEditor<?>> editableTypes;
	
	static {
		editableTypes = new ObjectMap<Class<?>, FieldEditor<?>>();
	}
	
	/** Registers a {@link FieldEditor} for its type
	 * @param fieldEditor */
	public static void register (FieldEditor<?> fieldEditor) {
		Class<?> type = fieldEditor.getType();
		editableTypes.put(type, fieldEditor);
	}
	
	/** @return */
	public abstract T get ();
	
	/** @return the type this edits */
	public abstract Class<T> getType ();
	
	/** Invoked when the value should be set
	 * @param object the {@link Object}
	 * @param field the {@link Field}
	 * @param value the value to set */
	public abstract void set (Object object, Field field, T value);
	
	@Override
	public abstract String toString ();
	
}
