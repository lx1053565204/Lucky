package xfl.fk.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Lucky {
	String id() default "";
	boolean auto() default true;
	String table() default "";
	String[] key() default {""};
	String[] url() default {""};
}
