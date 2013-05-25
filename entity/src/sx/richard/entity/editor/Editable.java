
package sx.richard.entity.editor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Denotes a field that is editable, and should appear in visual editors
 * @author Richard Taylor */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Editable {
	
	/** @return a description that can be displayed in the editor */
	public String description() default "";
	
	/** @return the name that will appear in the editor */
	public String name() default "";
	
	/** @return whether this field should appear in the editor, but be read-only */
	public boolean readOnly() default false;
	
	/** @return the type this points to, usage may vary */
	public Class<?> type() default Object.class;
	
}
