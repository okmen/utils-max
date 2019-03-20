package java.com.utils_max.decoder;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ResponsePosition {
    int length() default 0;

    String lengthParam() default "";

    int order();

    boolean ignore() default false;

    boolean isLoop() default false;

    String loopCountParam() default "";

    Class<?> msgClass() default Object.class;

}
