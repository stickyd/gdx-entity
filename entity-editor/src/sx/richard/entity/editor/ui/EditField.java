
package sx.richard.entity.editor.ui;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/** A {@link TextField} designed for editing
 * @author Richard Taylor */
public class EditField extends TextField {
	
	/** Allows only decimals
	 * @author Richard Taylor */
	public static class DecimalTextFieldFilter implements TextFieldFilter {
		
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
	}
	
	/** Listens out to changes in {@link EditField}
	 * @author Richard Taylor */
	public interface EditFieldListener {
		
		/** @param text the new text in the field */
		public void changed (String text);
		
		/** @return the original {@link String} value */
		public String getValue ();
	}
	
	private boolean editing;
	private EditFieldListener listener;
	
	{
		setStyle(new TextFieldStyle(getStyle()));
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
	
	/** @param skin the {@link Skin}
	 * @param listener the {@link EditFieldListener}, must not be
	 *           <code>null</code> */
	public EditField (Skin skin, EditFieldListener listener) {
		super(listener.getValue(), skin);
		setEditFieldListener(listener);
		setText(listener.getValue());
	}
	
	@Override
	public void act (float delta) {
		super.act(delta);
		if (editing) {
			if (getStage().getKeyboardFocus() != this) {
				editing = false;
				getStyle().fontColor.set(Color.WHITE);
				set();
			}
		} else {
			if (getStage().getKeyboardFocus() == this) {
				editing = true;
				getStyle().fontColor.set(Color.YELLOW);
			}
		}
		if (getStage().getKeyboardFocus() != this) {
			setText(listener.getValue());
		}
	}
	
	/** @param listener the {@link EditFieldListener} */
	public void setEditFieldListener (EditFieldListener listener) {
		this.listener = listener;
	}
	
	private void set () {
		listener.changed(getText());
		getStyle().fontColor.set(Color.WHITE);
	}
	
}
