package jasonzou.demo.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with Android Studio
 * User:邹旭
 * Date:2017/9/27
 * Time:23:50
 * Desc:略
 */


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    String name() default "";
    String type() default "varchar(20)";
    String comment() default "";
}
