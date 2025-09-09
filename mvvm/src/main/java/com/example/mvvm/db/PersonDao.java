package com.example.mvvm.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mvvm.model.Person;

import java.util.List;

/**
 * Room 数据库的数据访问对象 (DAO)。
 * DAO 是一个接口，其中定义了访问数据库的方法。
 * Room 会在编译时自动生成此接口的实现。
 */
@Dao
public interface PersonDao {

    /**
     * 插入 Person 对象到数据库。
     * @Insert 注解用于定义插入操作。
     * onConflict = OnConflictStrategy.REPLACE 表示如果插入的数据与现有数据发生冲突（例如主键相同），则替换现有数据。
     * @param person 要插入的 Person 对象。
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPerson(Person person);

    /**
     * 更新数据库中的 Person 对象。
     * @Update 注解用于定义更新操作。
     * @param person 要更新的 Person 对象。
     */
    @Update
    void updatePerson(Person person);

    /**
     * 从数据库中删除 Person 对象。
     * @Delete 注解用于定义删除操作。
     * @param person 要删除的 Person 对象。
     */
    @Delete
    void deletePerson(Person person);

    /**
     * 删除数据库中所有的 Person 记录。
     * @Query 注解用于执行自定义的 SQL 查询。
     * "DELETE FROM person" 是一个 SQL 语句，用于清空 person 表。
     */
    @Query("DELETE FROM person")
    void deleteAllPersons();

    /**
     * 获取数据库中所有的 Person 记录，并按姓名升序排序。
     * @Query 注解用于执行自定义的 SQL 查询。
     * 返回 LiveData<List<Person>> 表示这是一个可观察的数据，当数据库中的数据发生变化时，会自动通知观察者。
     * @return 包含所有 Person 对象的 LiveData 列表。
     */
    @Query("SELECT * FROM person ORDER BY name ASC")
    LiveData<List<Person>> getAllPersons();

    /**
     * 根据姓名查找 Person 对象。
     * @Query 注解用于执行自定义的 SQL 查询。
     * :name 表示这是一个参数，Room 会将方法参数 name 的值绑定到 SQL 查询中。
     * LIMIT 1 表示只返回一条匹配的记录。
     * 返回 LiveData<Person> 表示这是一个可观察的数据。
     * @param name 要查找的姓名。
     * @return 匹配姓名的 Person 对象的 LiveData。
     */
    @Query("SELECT * FROM person WHERE name LIKE :name LIMIT 1")
    LiveData<Person> findPersonByName(String name);
}
