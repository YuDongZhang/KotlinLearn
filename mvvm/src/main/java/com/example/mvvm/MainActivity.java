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

/**
 * 主活动 (MainActivity)，负责 UI 的展示和用户交互。
 * 它通过 ViewModel (PersonViewModel) 与 Room 数据库进行交互，遵循 MVVM 架构模式。
 */
public class MainActivity extends AppCompatActivity {

    // ViewBinding 实例，用于方便地访问布局中的视图元素
    private ActivityMainBinding binding;
    // ViewModel 实例，负责处理 UI 逻辑和数据操作，并与数据库交互
    private PersonViewModel personViewModel;
    // RecyclerView 的适配器，用于显示 Person 列表
    private PersonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化 ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 初始化 PersonViewModel。
        // ViewModelProvider 用于创建或获取 ViewModel 实例，确保 ViewModel 在配置更改（如屏幕旋转）时得以保留。
        personViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        // 将 ViewModel 设置给数据绑定，这样布局可以直接访问 ViewModel 中的数据和方法
        binding.setPersonViewModel(personViewModel);

        // 设置 RecyclerView
        setupRecyclerView();
        // 设置各种按钮的点击监听器
        setupListeners();
        // 设置滑动删除功能
        setupSwipeToDelete();

        // 观察 ViewModel 中的所有 Person 列表。
        // 当数据库中的数据发生变化时，LiveData 会通知这里的 Observer，然后更新 RecyclerView。
        personViewModel.getAllPersons().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> persons) {
                // 当数据变化时，提交新的列表给适配器，更新 UI
                adapter.submitList(persons);
            }
        });
    }

    /**
     * 设置 RecyclerView，包括适配器和布局管理器。
     */
    private void setupRecyclerView() {
        adapter = new PersonAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        // 设置 RecyclerView Item 的点击监听器
        adapter.setOnItemClickListener(new PersonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Person person) {
                // 当点击列表项时，将该 Person 的信息填充到输入框中，方便修改或删除
                binding.etName.setText(person.getName());
                binding.etAge.setText(String.valueOf(person.getAge()));
                binding.etGender.setText(person.getGender());
                binding.etJob.setText(person.getJob());
            }
        });
    }

    /**
     * 设置各种按钮的点击监听器，处理用户操作。
     */
    private void setupListeners() {
        // 添加按钮点击事件
        binding.btnAdd.setOnClickListener(v -> {
            // 获取输入框中的数据
            String name = binding.etName.getText().toString();
            int age = Integer.parseInt(binding.etAge.getText().toString());
            String gender = binding.etGender.getText().toString();
            String job = binding.etJob.getText().toString();

            // 创建 Person 对象
            Person person = new Person(name, age, gender, job);
            // 通过 ViewModel 插入 Person 到数据库
            personViewModel.insert(person);
            Toast.makeText(this, "Person added!", Toast.LENGTH_SHORT).show();
            // 清空输入框
            clearInputFields();
        });

        // 更新按钮点击事件
        binding.btnUpdate.setOnClickListener(v -> {
            String name = binding.etName.getText().toString();
            int age = Integer.parseInt(binding.etAge.getText().toString());
            String gender = binding.etGender.getText().toString();
            String job = binding.etJob.getText().toString();

            // 根据姓名查找要更新的 Person。
            // 注意：这里为了简化，假设通过姓名查找，实际应用中通常通过 ID 查找。
            personViewModel.findPersonByName(name).observe(this, new Observer<Person>() {
                @Override
                public void onChanged(Person personToUpdate) {
                    if (personToUpdate != null) {
                        // 更新 Person 对象的属性
                        personToUpdate.setName(name);
                        personToUpdate.setAge(age);
                        personToUpdate.setGender(gender);
                        personToUpdate.setJob(job);
                        // 通过 ViewModel 更新数据库中的 Person
                        personViewModel.update(personToUpdate);
                        Toast.makeText(MainActivity.this, "Person updated!", Toast.LENGTH_SHORT).show();
                        clearInputFields();
                    } else {
                        Toast.makeText(MainActivity.this, "Person not found for update!", Toast.LENGTH_SHORT).show();
                    }
                    // 移除观察者，防止多次触发更新（因为 LiveData 会在数据变化时一直通知）
                    personViewModel.findPersonByName(name).removeObserver(this);
                }
            });
        });

        // 删除按钮点击事件
        binding.btnDelete.setOnClickListener(v -> {
            String name = binding.etName.getText().toString();
            // 根据姓名查找要删除的 Person
            personViewModel.findPersonByName(name).observe(this, new Observer<Person>() {
                @Override
                public void onChanged(Person personToDelete) {
                    if (personToDelete != null) {
                        // 通过 ViewModel 删除数据库中的 Person
                        personViewModel.delete(personToDelete);
                        Toast.makeText(MainActivity.this, "Person deleted!", Toast.LENGTH_SHORT).show();
                        clearInputFields();
                    } else {
                        Toast.makeText(MainActivity.this, "Person not found for delete!", Toast.LENGTH_SHORT).show();
                    }
                    // 移除观察者
                    personViewModel.findPersonByName(name).removeObserver(this);
                }
            });
        });

        // 查询按钮点击事件
        binding.btnQuery.setOnClickListener(v -> {
            String name = binding.etName.getText().toString();
            if (!name.isEmpty()) {
                // 根据姓名查询 Person
                personViewModel.findPersonByName(name).observe(this, new Observer<Person>() {
                    @Override
                    public void onChanged(Person person) {
                        if (person != null) {
                            Toast.makeText(MainActivity.this, "Found: " + person.toString(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Person not found!", Toast.LENGTH_SHORT).show();
                        }
                        // 移除观察者
                        personViewModel.findPersonByName(name).removeObserver(this);
                    }
                });
            } else {
                Toast.makeText(this, "Enter a name to query!", Toast.LENGTH_SHORT).show();
            }
        });

        // 删除所有按钮点击事件
        binding.btnDeleteAll.setOnClickListener(v -> {
            // 通过 ViewModel 删除所有 Person
            personViewModel.deleteAllPersons();
            Toast.makeText(this, "All persons deleted!", Toast.LENGTH_SHORT).show();
            clearInputFields();
        });
    }

    /**
     * 设置 RecyclerView 的滑动删除功能。
     */
    private void setupSwipeToDelete() {
        // ItemTouchHelper 用于处理 RecyclerView 的滑动和拖拽事件
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) { // 允许向左或向右滑动
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // 不处理拖拽移动
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // 当 Item 被滑动删除时，获取对应的 Person 对象并从数据库中删除
                personViewModel.delete(adapter.getPersonAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Person deleted!", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(binding.recyclerView); // 将 ItemTouchHelper 附加到 RecyclerView
    }

    /**
     * 清空输入框中的文本。
     */
    private void clearInputFields() {
        binding.etName.setText("");
        binding.etAge.setText("");
        binding.etGender.setText("");
        binding.etJob.setText("");
    }
}
