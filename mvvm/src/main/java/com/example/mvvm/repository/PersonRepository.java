package com.example.mvvm.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.mvvm.db.PersonDao;
import com.example.mvvm.db.PersonDatabase;
import com.example.mvvm.model.Person;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PersonRepository {
    private PersonDao personDao;
    private LiveData<List<Person>> allPersons;
    private ExecutorService executorService;

    public PersonRepository(Application application) {
        PersonDatabase database = PersonDatabase.getInstance(application);
        personDao = database.personDao();
        allPersons = personDao.getAllPersons();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Person person) {
        executorService.execute(() -> personDao.insertPerson(person));
    }

    public void update(Person person) {
        executorService.execute(() -> personDao.updatePerson(person));
    }

    public void delete(Person person) {
        executorService.execute(() -> personDao.deletePerson(person));
    }

    public void deleteAllPersons() {
        executorService.execute(() -> personDao.deleteAllPersons());
    }

    public LiveData<List<Person>> getAllPersons() {
        return allPersons;
    }

    public LiveData<Person> findPersonByName(String name) {
        return personDao.findPersonByName(name);
    }
}
