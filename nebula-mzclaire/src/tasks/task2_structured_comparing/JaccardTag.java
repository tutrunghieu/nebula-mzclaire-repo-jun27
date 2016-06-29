package tasks.task2_structured_comparing;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JaccardTag {

	Class<? extends JaccardHelper> value();

	String name() default "";

}
