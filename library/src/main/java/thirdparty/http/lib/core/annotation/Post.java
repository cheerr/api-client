package thirdparty.http.lib.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by chenwei on 2017/6/9.
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface Post {

  String value() default "";
}
