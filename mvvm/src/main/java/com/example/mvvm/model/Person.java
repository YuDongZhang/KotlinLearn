package com.example.mvvm.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "person")
public class Person {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "age")
    public int age;

    @ColumnInfo(name = "gender")
    public String gender; // e.g., "Male", "Female"

    @ColumnInfo(name = "job")
    public String job; // e.g., "Programmer", "Waiter"

    public Person(String name, int age, String gender, String job) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.job = job;
    }

    // Getters for data binding (optional, but good practice)
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

    // Setter for id (Room will set this if autoGenerate is true)
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
