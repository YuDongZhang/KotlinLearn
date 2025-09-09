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

@Dao
public interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPerson(Person person);

    @Update
    void updatePerson(Person person);

    @Delete
    void deletePerson(Person person);

    @Query("DELETE FROM person")
    void deleteAllPersons();

    @Query("SELECT * FROM person ORDER BY name ASC")
    LiveData<List<Person>> getAllPersons();

    @Query("SELECT * FROM person WHERE name LIKE :name LIMIT 1")
    LiveData<Person> findPersonByName(String name);
}
