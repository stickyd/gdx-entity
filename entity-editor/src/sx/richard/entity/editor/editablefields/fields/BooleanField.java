
package sx.richard.entity.editor.editablefields.fields;

import sx.richard.entity.editor.Assets;
import sx.richard.entity.editor.EditableField;
import sx.richard.entity.editor.EditableUtils;
import sx.richard.entity.editor.editablefields.EditableFieldFactory;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/** Edits a {@link Boolean} field, displaying a checkbox
 * @author Richard Taylor */
public class BooleanField extends EditableFieldFactory<Boolean> {
	
	/** @return the {@link Actor} to edit a {@link Boolean} */
	public static Actor field (final EditableField field, final Object object, final Boolean value) {
		return new CheckBox("", Assets.skin) {
			
			{
				setChecked(value);
				addListener(new ClickListener() {
					
					@Override
					public void clicked (InputEvent e, float x, float y) {
						EditableUtils.set(field.field, object, isChecked());
					}
				});
			}
		};
	}
	
	@Override
	protected Actor create (EditableField field, Object object, Boolean value) {
		return field(field, object, value);
	}
}
