
package sx.richard.entity.editor;

import java.lang.reflect.Field;

/** Contains a {@link Field}, which is {@link Editable}
 * @author Richard Taylor */
public class EditableField {
	
	/** The {@link Editable} */
	public final Editable editable;
	/** The {@link Field} */
	public final Field field;
	
	/** @param editable the {@link Editable}
	 * @param field the {@link Field} */
	public EditableField (Editable editable, Field field) {
		if (field == null)
			throw new NullPointerException("Field must not be null");
		this.editable = editable;
		this.field = field;
	}
	
	/** @param field the {@link Field} */
	public EditableField (Field field) {
		this(null, field);
	}
	
	@Override
	public String toString () {
		return "[EditableField field=" + field + " editable=" + editable + "]";
	}
}