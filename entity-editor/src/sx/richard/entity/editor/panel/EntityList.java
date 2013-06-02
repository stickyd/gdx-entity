
package sx.richard.entity.editor.panel;

import sx.richard.entity.Entity;
import sx.richard.entity.EntityGroup;
import sx.richard.entity.World;
import sx.richard.entity.editor.Assets;
import sx.richard.entity.editor.Editor;
import sx.richard.entity.editor.events.EntitySelected;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.TreeStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/** Lists all the entities in the {@link World}, allows selection. When an entity
 * is selected, publishes the {@link EntitySelected}
 * @author Richard Taylor */
public class EntityList extends Table {
	
	private Tree tree;
	private final World world;
	
	/** @param world the {@link World} */
	public EntityList (World world) {
		if (world == null)
			throw new NullPointerException("World must not be null");
		this.world = world;
		setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("background.png"))));
		createTree();
	}
	
	private void add (Node parent, Entity entity) {
		if (!entity.getId().startsWith("_")) {
			Node node = new Node(entity(entity));
			node.setObject(entity);
			parent.add(node);
		}
	}
	
	private ChangeListener changeListener (final Tree tree) {
		return new ChangeListener() {
			
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				Node node = tree.getSelection().size > 0 ? tree.getSelection().first() : null;
				Entity entity = null;
				if (node != null) {
					entity = (Entity)node.getObject();
				}
				Editor.events.post(new EntitySelected(entity));
			}
			
		};
	}
	
	private void createTree () {
		Drawable plus = new TextureRegionDrawable(new TextureRegion(new Texture("plus.png")));
		Drawable minus = new TextureRegionDrawable(new TextureRegion(new Texture("minus.png")));
		TextureRegionDrawable selected = new TextureRegionDrawable(new TextureRegion(new Texture("selected.png")));
		tree = new Tree(new TreeStyle(plus, minus, selected));
		add(tree).expand().fill();
		tree.addListener(changeListener(tree));
		populateTree();
	}
	
	private Actor entity (Entity entity) {
		return new Label(entity.getId(), Assets.skin);
	}
	
	private void populateTree () {
		for (Entity entity : world.getEntities()) {
			if (!entity.getId().startsWith("_")) {
				Node node = new Node(entity(entity));
				node.setObject(entity);
				tree.add(node);
				populateTree(node, entity);
			}
		}
	}
	
	private void populateTree (Node parent, EntityGroup group) {
		for (Entity entity : group.getEntities()) {
			add(parent, entity);
		}
	}
	
}
