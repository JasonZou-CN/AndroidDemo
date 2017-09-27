package jasonzou.demo.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with Android Studio
 * User:邹旭
 * Date:2017/9/27
 * Time:23:19
 * Desc:略
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    /**
     * 数据表名称注解，默认值为类名称
     *
     * @return
     */
    String tableName() default "table";
}
