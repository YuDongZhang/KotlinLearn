package com.example.mvvm.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Room 数据库的实体类 (Entity)。
 * 每个 @Entity 注解的类都代表数据库中的一张表。
 * 默认情况下，表名与类名相同，但可以通过 tableName 属性指定。
 */
@Entity(tableName = "person") // 指定这张表在数据库中的名称为 "person"
public class Person {

    /**
     * 主键 (Primary Key)。
     * 每个表都必须有一个主键。
     * autoGenerate = true 表示当插入新数据时，Room 会自动生成一个唯一的 ID。
     */
    @PrimaryKey(autoGenerate = true)
    public int id;

    /**
     * 列信息 (ColumnInfo)。
     * @ColumnInfo 注解用于指定字段在数据库表中的列名。
     * 如果不指定，默认使用字段名作为列名。
     */
    @ColumnInfo(name = "name") // 指定列名为 "name"
    public String name;

    @ColumnInfo(name = "age") // 指定列名为 "age"
    public int age;

    @ColumnInfo(name = "gender") // 指定列名为 "gender"
    public String gender; // e.g., "Male", "Female"

    @ColumnInfo(name = "job") // 指定列名为 "job"
    public String job; // e.g., "Programmer", "Waiter"

    /**
     * 构造函数。
     * Room 在创建实体对象时会使用这个构造函数。
     * 注意：如果主键是 autoGenerate = true，则构造函数中不需要包含主键字段。
     */
    public Person(String name, int age, String gender, String job) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.job = job;
    }

    // --- Getter 方法 ---
    // Room 在读取数据并映射到实体对象时，会使用这些 Getter 方法。
    // 它们对于数据绑定（如 LiveData 或 ViewModel）也很有用。
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getJob() {
        return job;
    }

    // --- Setter 方法 ---
    // Room 在更新数据时可能会使用 Setter 方法。
    // 对于 autoGenerate 的主键，Room 会在插入后自动设置 ID。
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setJob(String job) {
        this.job = job;
    }

    /**
     * toString 方法，方便打印 Person 对象的信息，用于调试。
     */
    @Override
    public String toString() {
        return "Person{"
                + "id=" + id +
                ", name='" + name + "'" +
                ", age=" + age +
                ", gender='" + gender + "'" +
                ", job='" + job + "'" +
                '}';
    }
}
