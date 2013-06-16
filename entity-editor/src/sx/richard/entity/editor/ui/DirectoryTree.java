
package sx.richard.entity.editor.ui;

import sx.richard.entity.editor.Assets;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ObjectMap;

/** Lists directories and subdirectories relative to a root, provides a callback
 * to selection
 * @author Richard Taylor */
public class DirectoryTree extends Table {
	
	/** Callbacks for the {@link DirectoryTree}
	 * @author Richard Taylor */
	public interface DirectoryTreeListener {
		
		/** Invoked when a directory is selected
		 * @param directory the {@link FileHandle} of the directory */
		public void directorySelected (FileHandle directory);
		
	}
	
	private class DirectoryTreeNode extends Node {
		
		private DirectoryTreeNode (final FileHandle file) {
			super(new Table() {
				
				{
					add(new Label(file.name(), Assets.skin));
				}
			});
			setObject(file);
			setExpanded(true);
			nodes.put(file.path(), this);
		}
		
	}
	
	private final DirectoryTreeListener listener;
	private final ObjectMap<String, DirectoryTreeNode> nodes = new ObjectMap<String, DirectoryTreeNode>();
	private final FileHandle root;
	private Tree tree;
	
	public DirectoryTree (FileHandle root, FileHandle selected, final DirectoryTreeListener listener) {
		this.root = root;
		this.listener = listener;
		create(selected);
		addListener(new ClickListener() {
			
			@Override
			public void clicked (InputEvent e, float x, float y) {
				Node node = tree.getSelection().first();
				if (node != null) {
					FileHandle file = (FileHandle)node.getObject();
					listener.directorySelected(file);
				}
			}
		});
	}
	
	private void addChildren (Node n, FileHandle root) {
		for (FileHandle file : root.list()) {
			if (file.isDirectory()) {
				Node node = new DirectoryTreeNode(file);
				n.add(node);
				addChildren(node, file);
			}
		}
	}
	
	private void create (final FileHandle selected) {
		setBackground(Assets.skin.newDrawable("white", new Color(0.15f, 0.15f, 0.15f, 1f)));
		add(tree = new Tree(Assets.skin)).expand().fill();
		for (FileHandle file : root.list()) {
			if (file.isDirectory()) {
				Node node = new DirectoryTreeNode(file);
				tree.add(node);
				addChildren(node, file);
			}
		}
		if (selected != null) {
			DirectoryTreeNode node = nodes.get(selected.path());
			if (node != null) {
				node.setExpanded(true);
				tree.setSelection(node);
				listener.directorySelected(selected);
			}
		}
	}
	
}
