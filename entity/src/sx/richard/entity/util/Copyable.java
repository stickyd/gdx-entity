
package sx.richard.entity.util;

/** An object that can provide a deep copy
 * @author Richard Taylor */
public interface Copyable<T extends Copyable<T>> {
	
	/** @return a deep copy */
	public T copy ();
	
}
