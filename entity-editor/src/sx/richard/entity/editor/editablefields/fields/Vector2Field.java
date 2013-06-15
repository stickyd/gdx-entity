
package sx.richard.entity.editor.editablefields.fields;

import sx.richard.entity.editor.Assets;
import sx.richard.entity.editor.EditableField;
import sx.richard.entity.editor.editablefields.EditableFieldFactory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/** Creates the field to edit {@link Vector2}s
 * @author Richard Taylor */
public class Vector2Field extends EditableFieldFactory<Vector2> {
	
	@Override
	protected Actor create (EditableField field, Object object, Vector2 value) {
		try {
			EditableField x = new EditableField(null, field.field.getType().getDeclaredField("x"));
			EditableField y = new EditableField(null, field.field.getType().getDeclaredField("y"));
			final Actor xField = FloatField.floatField(x, value, value.x);
			final Actor yField = FloatField.floatField(y, value, value.x);
			
			return new Table() {
				
				{
					add(new Label("x", Assets.skin));
					add(xField);
					row();
					add(new Label("y", Assets.skin));
					add(yField);
				}
			};
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
