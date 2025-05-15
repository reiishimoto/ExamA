package tool;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChainAction {
	public static final String KEY = "onetime_structure_key";

	boolean isRoot() default false;
	Class<? extends Action> rootClass() default Action.class;
	String redirectFor() default "Menu.action";
	boolean isEnd() default false;
}
