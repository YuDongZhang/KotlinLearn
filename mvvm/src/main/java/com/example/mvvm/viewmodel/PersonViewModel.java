package com.example.mvvm.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mvvm.model.Person;
import com.example.mvvm.repository.PersonRepository;

import java.util.List;

public class PersonViewModel extends AndroidViewModel {
    private PersonRepository repository;
    private LiveData<List<Person>> allPersons;

    public PersonViewModel(@NonNull Application application) {
        super(application);
        repository = new PersonRepository(application);
        allPersons = repository.getAllPersons();
    }

    public void insert(Person person) {
        repository.insert(person);
    }

    public void update(Person person) {
        repository.update(person);
    }

    public void delete(Person person) {
        repository.delete(person);
    }

    public void deleteAllPersons() {
        repository.deleteAllPersons();
    }

    public LiveData<List<Person>> getAllPersons() {
        return allPersons;
    }

    public LiveData<Person> findPersonByName(String name) {
        return repository.findPersonByName(name);
    }
}
