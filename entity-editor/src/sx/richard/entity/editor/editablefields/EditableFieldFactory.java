
package sx.richard.entity.editor.editablefields;

import sx.richard.entity.assets.Asset;
import sx.richard.entity.editor.Assets;
import sx.richard.entity.editor.EditableField;
import sx.richard.entity.editor.EditableUtils;
import sx.richard.entity.editor.editablefields.fields.AssetField;
import sx.richard.entity.editor.editablefields.fields.BooleanField;
import sx.richard.entity.editor.editablefields.fields.ColorField;
import sx.richard.entity.editor.editablefields.fields.FloatField;
import sx.richard.entity.editor.editablefields.fields.Vector2Field;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ObjectMap;

/** Creates the actor that will edit a field
 * @author Richard Taylor */
public abstract class EditableFieldFactory<T> {
	
	private static final ObjectMap<String, EditableFieldFactory<?>> editableFieldFactories;
	
	static {
		editableFieldFactories = new ObjectMap<String, EditableFieldFactory<?>>();
		registerDefaults();
	}
	
	/** @param type the type
	 * @return the {@link EditableFieldFactory}, <code>null</code> if it there is
	 *         none registered */
	public static EditableFieldFactory<?> get (Class<?> type) {
		if (type == null)
			throw new NullPointerException("Type must not be null");
		return editableFieldFactories.get(type.getName());
	}
	
	/** Registers an {@link EditableFieldFactory} as the factory for a particular
	 * type
	 * @param type the type
	 * @param factory the {@link EditableFieldFactory} */
	public static void register (Class<?> type, EditableFieldFactory<?> factory) {
		if (type == null)
			throw new NullPointerException("Type must not be null");
		if (factory == null)
			throw new NullPointerException("Factory must not be null");
		editableFieldFactories.put(type.getName(), factory);
	}
	
	private static void register (String type, EditableFieldFactory<?> factory) {
		editableFieldFactories.put(type, factory);
	}
	
	private static void registerDefaults () {
		register("float", new FloatField());
		register(Float.class, new FloatField());
		register("boolean", new BooleanField());
		register(Boolean.class, new BooleanField());
		register(Vector2.class, new Vector2Field());
		register(Color.class, new ColorField());
		register(Asset.class, new AssetField());
	}
	
	/** @param field the {@link EditableFieldFactory}
	 * @param object the {@link Object} */
	@SuppressWarnings("unchecked")
	public Actor create (final EditableField field, final Object object) {
		Object value = EditableUtils.get(field.field, object);
		if (value == EditableUtils.ERROR)
			return new Label("[error]", Assets.skin);
		return create(field, object, (T)value);
	}
	
	/** @param field the {@link EditableField}
	 * @param object the {@link Object} this field is in
	 * @param value the current value
	 * @return the {@link Actor} */
	protected abstract Actor create (final EditableField field, final Object object, final T value);
	
}
