
package sx.richard.entity.editor.editablefields;

import java.lang.reflect.Field;

import sx.richard.entity.editor.Assets;
import sx.richard.entity.editor.EditableUtils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/** Edits a floating point value
 * @author Richard Taylor */
public class FloatField extends EditableFieldFactory<Float> {
	
	@Override
	protected Actor create (final Field field, final Object object, final Float value) {
		return new TextField("0", Assets.skin) {
			
			{
				final TextField textField = this;
				final TextFieldStyle style = new TextFieldStyle(getStyle());
				setStyle(style);
				setText(value.toString());
				setTextFieldFilter(new TextFieldFilter() {
					
					@Override
					public boolean acceptChar (TextField field, char c) {
						switch (c) {
							case '0':
							case '1':
							case '2':
							case '3':
							case '4':
							case '5':
							case '6':
							case '7':
							case '8':
							case '9':
							case '.':
							case '-':
								return true;
						}
						return false;
					}
				});
				addListener(new ClickListener() {
					
					@Override
					public boolean keyTyped (InputEvent event, char c) {
						String text = textField.getText();
						try {
							Float value = Float.valueOf(text);
							EditableUtils.set(field, object, value);
							style.fontColor.set(Color.WHITE);
						} catch (Exception e) {
							style.fontColor.set(Color.RED);
						}
						return true;
					}
				});
			}
		};
	}
	
}
