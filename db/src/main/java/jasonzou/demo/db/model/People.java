package jasonzou.demo.db.model;

import jasonzou.demo.db.annotation.Column;
import jasonzou.demo.db.annotation.Table;

/**
 * Created with Android Studio
 * User:邹旭
 * Date:2017/9/27
 * Time:23:52
 * Desc:略
 */
@Table(tableName = "person")
public class People {

    @Column(name = "name", type = "text", comment = "姓名")
    String name;

    @Column(name = "age", type = "integer", comment = "年龄")
    int age;
}
