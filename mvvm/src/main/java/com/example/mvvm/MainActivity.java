package com.example.mvvm;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mvvm.databinding.ActivityMainBinding;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm.adapter.PersonAdapter;
import com.example.mvvm.databinding.ActivityMainBinding;
import com.example.mvvm.model.Person;
import com.example.mvvm.viewmodel.PersonViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PersonViewModel personViewModel;
    private PersonAdapter adapter;

        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        personViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        binding.setPersonViewModel(personViewModel); // Set ViewModel for data binding

        setupRecyclerView();
        setupListeners();
        setupSwipeToDelete();

        personViewModel.getAllPersons().observe(this, new Observer<List<Person>>() {
            
            public void onChanged(List<Person> persons) {
                adapter.submitList(persons);
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new PersonAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new PersonAdapter.OnItemClickListener() {
            
            public void onItemClick(Person person) {
                // Populate input fields for update/delete
                binding.etName.setText(person.getName());
                binding.etAge.setText(String.valueOf(person.getAge()));
                binding.etGender.setText(person.getGender());
                binding.etJob.setText(person.getJob());
            }
        });
    }

    private void setupListeners() {
        binding.btnAdd.setOnClickListener(v -> {
            String name = binding.etName.getText().toString();
            int age = Integer.parseInt(binding.etAge.getText().toString());
            String gender = binding.etGender.getText().toString();
            String job = binding.etJob.getText().toString();

            Person person = new Person(name, age, gender, job);
            personViewModel.insert(person);
            Toast.makeText(this, "Person added!", Toast.LENGTH_SHORT).show();
            clearInputFields();
        });

        binding.btnUpdate.setOnClickListener(v -> {
            String name = binding.etName.getText().toString();
            int age = Integer.parseInt(binding.etAge.getText().toString());
            String gender = binding.etGender.getText().toString();
            String job = binding.etJob.getText().toString();

            // Assuming you select a person from the list to update
            // For simplicity, we'll update the first person found with the given name
            personViewModel.findPersonByName(name).observe(this, new Observer<Person>() {
                
                public void onChanged(Person personToUpdate) {
                    if (personToUpdate != null) {
                        personToUpdate.setName(name);
                        personToUpdate.setAge(age);
                        personToUpdate.setGender(gender);
                        personToUpdate.setJob(job);
                        personViewModel.update(personToUpdate);
                        Toast.makeText(MainActivity.this, "Person updated!", Toast.LENGTH_SHORT).show();
                        clearInputFields();
                    } else {
                        Toast.makeText(MainActivity.this, "Person not found for update!", Toast.LENGTH_SHORT).show();
                    }
                    // Remove observer to prevent multiple updates
                    personViewModel.findPersonByName(name).removeObserver(this);
                }
            });
        });

        binding.btnDelete.setOnClickListener(v -> {
            String name = binding.etName.getText().toString();
            personViewModel.findPersonByName(name).observe(this, new Observer<Person>() {
                
                public void onChanged(Person personToDelete) {
                    if (personToDelete != null) {
                        personViewModel.delete(personToDelete);
                        Toast.makeText(MainActivity.this, "Person deleted!", Toast.LENGTH_SHORT).show();
                        clearInputFields();
                    } else {
                        Toast.makeText(MainActivity.this, "Person not found for delete!", Toast.LENGTH_SHORT).show();
                    }
                    personViewModel.findPersonByName(name).removeObserver(this);
                }
            });
        });

        binding.btnQuery.setOnClickListener(v -> {
            String name = binding.etName.getText().toString();
            if (!name.isEmpty()) {
                personViewModel.findPersonByName(name).observe(this, new Observer<Person>() {
                    
                    public void onChanged(Person person) {
                        if (person != null) {
                            Toast.makeText(MainActivity.this, "Found: " + person.toString(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Person not found!", Toast.LENGTH_SHORT).show();
                        }
                        personViewModel.findPersonByName(name).removeObserver(this);
                    }
                });
            } else {
                Toast.makeText(this, "Enter a name to query!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnDeleteAll.setOnClickListener(v -> {
            personViewModel.deleteAllPersons();
            Toast.makeText(this, "All persons deleted!", Toast.LENGTH_SHORT).show();
            clearInputFields();
        });
    }

    private void setupSwipeToDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                personViewModel.delete(adapter.getPersonAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Person deleted!", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(binding.recyclerView);
    }

    private void clearInputFields() {
        binding.etName.setText("");
        binding.etAge.setText("");
        binding.etGender.setText("");
        binding.etJob.setText("");
    }
}