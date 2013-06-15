
package sx.richard.entity.editor.editablefields;

import sx.richard.entity.editor.Assets;
import sx.richard.entity.editor.EditableField;
import sx.richard.entity.editor.EditableUtils;
import sx.richard.entity.editor.SetValue;
import sx.richard.entity.editor.actions.EditorActions;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/** Edits a floating point value
 * @author Richard Taylor */
public class FloatField extends EditableFieldFactory<Float> {
	
	public static Actor floatField (final EditableField field, final Object object, final Float value) {
		return new TextField("0", Assets.skin) {
			
			boolean editing;
			TextFieldStyle style;
			{
				style = new TextFieldStyle(getStyle());
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
					public boolean keyDown (InputEvent event, int keycode) {
						if (keycode == Keys.ENTER) {
							set();
							getStage().setKeyboardFocus(null);
						}
						return false;
					}
				});
			}
			
			@Override
			public void act (float delta) {
				super.act(delta);
				if (editing) {
					if (getStage().getKeyboardFocus() != this) {
						editing = false;
						style.fontColor.set(Color.WHITE);
						set();
					}
				} else {
					if (getStage().getKeyboardFocus() == this) {
						editing = true;
						style.fontColor.set(Color.YELLOW);
					}
				}
				if (getStage().getKeyboardFocus() != this) {
					setText(String.format("%.2f", EditableUtils.get(field.field, object)));
				}
			}
			
			private void set () {
				String text = getText().trim();
				if (text.length() == 0) {
					text = "0";
				}
				setText(text);
				try {
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
					style.fontColor.set(Color.WHITE);
				} catch (Exception e) {
					style.fontColor.set(Color.RED);
				}
			}
		};
	}
	
	@Override
	protected Actor create (final EditableField field, final Object object, final Float value) {
		return floatField(field, object, value);
	}
	
}
