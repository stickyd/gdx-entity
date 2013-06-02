
package sx.richard.entity.editor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.badlogic.gdx.utils.Array;

/** Util methods for {@link Editable}s
 * @author Richard Taylor */
public class EditableUtils {
	
	/** @param classObject the objects {@link Class}
	 * @return the {@link Editable} fields in this {@link Class} */
	public static Array<EditableField> getEditables (Class<?> classObject) {
		Array<EditableField> editableFields = new Array<EditableField>();
		for (Field field : classObject.getDeclaredFields()) {
			for (Annotation annotation : field.getAnnotations()) {
				if (annotation instanceof Editable) {
					editableFields.add(new EditableField((Editable)annotation, field));
					break;
				}
			}
		}
		return editableFields;
	}
	
	/** Sets a field, fixes access modifiers
	 * @param field the {@link Field}
	 * @param object the {@link Object}
	 * @param value the value */
	public static void set (Field field, Object object, Object value) {
		try {
			boolean hackPrivate = false;
			if (!field.isAccessible()) {
				hackPrivate = true;
				field.setAccessible(true);
			}
			field.set(object, value);
			System.out.println("Set, " + value);
			if (hackPrivate) {
				field.setAccessible(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
