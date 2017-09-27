package jasonzou.demo.db.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created with Android Studio
 * User:邹旭
 * Date:2017/9/27
 * Time:23:56
 * Desc:略
 */

public class AnnotationHandler {
    public static void initTable() {
        StringBuilder sql = new StringBuilder("CREATE TABLE ");
        try {
            Class clazz = Class.forName("jasonzou.demo.db.model.People");
            //获取Users类上的Table注解
            Table tableAnnotation = (Table) clazz.getAnnotation(Table.class);
            //获取表名
            String tableName = tableAnnotation.tableName().toLowerCase();
            if ("".equals(tableName)) {
                tableName = clazz.getName().toLowerCase();
            }
            sql.append(tableName);
            sql.append(" ( \n");
            //获取类中的所有字段
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                //获取字段上的所有注解
                Annotation[] fieldAnnotations = field.getAnnotations();
                if (fieldAnnotations.length > 0) {
                    //遍历注解
                    for (Annotation fieldAnnotation : fieldAnnotations) {
                        //如果是@Field注解，那么进行处理
                        if (fieldAnnotation instanceof Column) {
                            //获取字段名
                            String columnName = ((Column) fieldAnnotation).name().toLowerCase();
                            if ("".equals(columnName)) {
                                columnName = field.getName().toLowerCase();
                            }
                            //获取数据类型
                            String dataType = ((Column) fieldAnnotation).type().toLowerCase();
                            //获取注释
                            String comment = ((Column) fieldAnnotation).comment();
                            if ("".equals(comment)) {
                                sql.append(columnName + "\t" + dataType + ",\n");
                            } else {
                                sql.append(columnName + "\t" + dataType + " COMMENT '" + comment + "',\n");
                            }
                        }
                    }
                }
            }
            sql.append(" ) ");
            System.out.println("生成的sql语句为：\n" + sql.toString());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
