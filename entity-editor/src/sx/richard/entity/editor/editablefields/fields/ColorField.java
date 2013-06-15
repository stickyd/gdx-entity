
package sx.richard.entity.editor.editablefields.fields;

import javax.swing.JColorChooser;
import javax.swing.JFrame;

import sx.richard.entity.editor.Assets;
import sx.richard.entity.editor.EditableField;
import sx.richard.entity.editor.EditableUtils;
import sx.richard.entity.editor.editablefields.EditableFieldFactory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/** Edits a {@link Color}
 * @author Richard Taylor */
public class ColorField extends EditableFieldFactory<Color> {
	
	@Override
	protected Actor create (final EditableField field, final Object object, final Color v) {
		return new Table() {
			
			{
				Table t;
				setBackground(Assets.skin.newDrawable("white"));
				final Color value = v == null ? Color.WHITE : v;
				add(t = new Table() {
					
					{
						setBackground(Assets.skin.newDrawable("white", value));
					}
				}).expand().fill().pad(2);
				final Table table = t;
				setTouchable(Touchable.enabled);
				addListener(new ClickListener() {
					
					@Override
					public void clicked (InputEvent e, float x, float y) {
						JFrame frame = new JFrame();
						Color a = (Color)EditableUtils.get(field.field, object);
						if (a == null) {
							a = value;
						}
						java.awt.Color cc = new java.awt.Color(a.r, a.g, a.b, a.a);
						java.awt.Color c = JColorChooser.showDialog(frame, "Pick a color", cc);
						if (c != null) {
							Color ccc = new Color(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
							EditableUtils.set(field.field, object, ccc);
							table.setBackground(Assets.skin.newDrawable("white", ccc));
						}
					}
				});
			}
			
		};
	}
}
