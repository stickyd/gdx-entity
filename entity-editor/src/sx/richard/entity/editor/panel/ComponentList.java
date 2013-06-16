
package sx.richard.entity.editor.panel;

import sx.richard.entity.Component;
import sx.richard.entity.Entity;
import sx.richard.entity.editor.Assets;
import sx.richard.entity.editor.EditableField;
import sx.richard.entity.editor.EditableUtils;
import sx.richard.entity.editor.Editor;
import sx.richard.entity.editor.editablefields.EditableFieldFactory;
import sx.richard.entity.editor.events.EntitySelected;
import sx.richard.entity.editor.ui.EditField;
import sx.richard.entity.editor.ui.EditField.AlphaNumericTextFieldFilter;
import sx.richard.entity.editor.ui.EditField.EditFieldListener;
import sx.richard.eventbus.EventListener;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

/** Contains the foldable component list, follows entity selection
 * @author Richard Taylor */
public class ComponentList extends Table {
	
	private Table components;
	private Entity entity;
	
	public ComponentList () {
		setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("background.png"))));
		Editor.events.register(this, EntitySelected.class, entitySelected());
		defaults().expand().fillX().top();
	}
	
	/** @return the {@link Entity} */
	public Entity getEntity () {
		return entity;
	}
	
	/** @param entity the {@link Entity} */
	public void setEntity (Entity entity) {
		this.entity = entity;
		clearComponents();
		if (entity != null) {
			components = new Table();
			addHeader();
			populateComponents();
		}
	}
	
	private void add (final Class<? extends Component<?>> componentType) {
		final Component<?> component = entity.get(componentType);
		if (component.isHidden())
			return;
		components.add(new Table() {
			
			{
				defaults().expandX().left().padLeft(5);
				add(new Label(componentType.getSimpleName(), Assets.skin));
				row();
				add(new Table() {
					
					{
						defaults().expandX().fill().left();
						Array<EditableField> fields = EditableUtils.getEditables(componentType);
						for (EditableField field : fields) {
							add(new Label(field.field.getName(), Assets.skin));
							Actor actor = null;
							EditableFieldFactory<?> factory = EditableFieldFactory.get(field.field.getType());
							if (factory != null) {
								actor = factory.create(field, component);
							}
							if (actor != null) {
								add(actor).expandX().fill();
							} else {
								add(new Label("--- " + field.field.getType().getName(), Assets.skin));
							}
							row();
						}
					}
				}).expand().fill();
			}
		}).expandX().fill().pad(2);
		components.row();
	}
	
	private void addHeader () {
		components.add(new Table() {
			
			{
				final EditField field = new EditField(Assets.skin, new EditFieldListener() {
					
					@Override
					public void changed (String text) {
						entity.setId(text);
					}
					
					@Override
					public String getValue () {
						return entity != null ? entity.getId() : "---";
					}
				});
				field.setTextFieldFilter(new AlphaNumericTextFieldFilter());
				add(field).expand().fill();
			}
		}).expandX().fill();
		components.row();
	}
	
	private void clearComponents () {
		if (components != null) {
			clear();
			components = null;
		}
	}
	
	private EventListener<EntitySelected> entitySelected () {
		return new EventListener<EntitySelected>() {
			
			@Override
			public void onEvent (EntitySelected event) {
				setEntity(event.entity);
			}
		};
	}
	
	private void populateComponents () {
		add(components);
		for (Class<? extends Component<?>> componentType : entity.getComponents()) {
			add(componentType);
		}
	}
}
