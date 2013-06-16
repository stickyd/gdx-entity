
package sx.richard.entity.editor.editablefields.fields;

import sx.richard.entity.editor.Assets;
import sx.richard.entity.editor.EditableField;
import sx.richard.entity.editor.EditableUtils;
import sx.richard.entity.editor.SetValue;
import sx.richard.entity.editor.actions.EditorActions;
import sx.richard.entity.editor.editablefields.EditableFieldFactory;
import sx.richard.entity.editor.ui.EditField;
import sx.richard.entity.editor.ui.EditField.DecimalTextFieldFilter;
import sx.richard.entity.editor.ui.EditField.EditFieldListener;

import com.badlogic.gdx.scenes.scene2d.Actor;

/** Edits a floating point value
 * @author Richard Taylor */
public class FloatField extends EditableFieldFactory<Float> {
	
	public static Actor floatField (final EditableField field, final Object object, final Float value) {
		EditField f = new EditField(Assets.skin, new EditFieldListener() {
			
			@Override
			public void changed (String text) {
				text = text.trim();
				if (text.length() == 0) {
					text = "0";
				}
				Float value = Float.valueOf(text);
				if (field.editable != null) {
					if (field.editable.min() != Float.MIN_VALUE) {
						if (value < field.editable.min())
							throw new IllegalArgumentException("Minimum value is " + Float.MIN_VALUE);
					} else if (field.editable.max() != Float.MAX_VALUE) {
						if (value > field.editable.max())
							throw new IllegalArgumentException("Maximum value is " + Float.MAX_VALUE);
					}
				}
				EditorActions.run(new SetValue(field.field, object, value));
			}
			
			@Override
			public String getValue () {
				return String.format("%.2f", EditableUtils.get(field.field, object));
			}
		});
		f.setTextFieldFilter(new DecimalTextFieldFilter());
		return f;
	}
	
	@Override
	protected Actor create (final EditableField field, final Object object, final Float value) {
		return floatField(field, object, value);
	}
	
}
