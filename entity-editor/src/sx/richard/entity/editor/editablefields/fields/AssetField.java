
package sx.richard.entity.editor.editablefields.fields;

import java.lang.reflect.ParameterizedType;

import sx.richard.entity.Component;
import sx.richard.entity.Engine;
import sx.richard.entity.assets.Asset;
import sx.richard.entity.assets.AssetType;
import sx.richard.entity.editor.Assets;
import sx.richard.entity.editor.EditableField;
import sx.richard.entity.editor.EditableUtils;
import sx.richard.entity.editor.Editor;
import sx.richard.entity.editor.assets.AssetPickerWindow;
import sx.richard.entity.editor.assets.AssetPickerWindow.AssetPickerListener;
import sx.richard.entity.editor.editablefields.EditableFieldFactory;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/** Selects an {@link Asset}
 * @author Richard Taylor */
public class AssetField extends EditableFieldFactory<Asset<?>> {
	
	@Override
	protected Actor create (final EditableField field, final Object object, Asset<?> value) {
		return new TextButton(value != null ? value.file().nameWithoutExtension() : "[none]", Assets.skin) {
			
			{
				addListener(new ClickListener() {
					
					@Override
					public void clicked (InputEvent e, float x, float y) {
						final Class<?> typeClass = (Class<?>)((ParameterizedType)field.field.getGenericType()).getActualTypeArguments()[0];
						final AssetType type = AssetType.from(typeClass);
						AssetPickerWindow window = new AssetPickerWindow(type, new AssetPickerListener() {
							
							@Override
							public void cancel () {}
							
							@SuppressWarnings({ "unchecked", "rawtypes" })
							@Override
							public void ok (FileHandle file) {
								Asset<?> existing = (Asset<?>)EditableUtils.get(field.field, object);
								EditableUtils.set(field.field, object, new Asset(file != null ? file.path() : null, typeClass));
								setText(file != null ? file.nameWithoutExtension() : "[none]");
								if (object instanceof Component) {
									((Component)object).edited();
								}
								if (existing != null) {
									Engine.getAssetManager().unload(existing);
								}
							}
						});
						Editor.push(window);
					}
				});
			}
		};
	}
}
