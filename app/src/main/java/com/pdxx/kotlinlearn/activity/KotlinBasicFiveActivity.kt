package com.pdxx.kotlinlearn.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.adapter.KotlinBasicAdapter
import com.pdxx.kotlinlearn.data.Lesson
import com.pdxx.kotlinlearn.databinding.ActivityKotlinBasicFiveBinding

class KotlinBasicFiveActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val binding = DataBindingUtil.setContentView<ActivityKotlinBasicFiveBinding>(
            this, R.layout.activity_kotlin_basic_five
        )
        
        // 创建Kotlin实战应用课程列表
        val practicalLessons = listOf( 
            Lesson( 
                title = "Android 中使用协程", 
                concept = """ 
 在 Android 中，协程常用于网络请求、数据库操作等异步任务。  
 通过 `lifecycleScope` 或 `viewModelScope` 启动协程，避免内存泄漏。  
 `Dispatchers` 控制协程运行线程： 
 - `Dispatchers.Main`：主线程（UI 操作） 
 - `Dispatchers.IO`：IO 线程（网络、文件） 
 - `Dispatchers.Default`：计算密集任务 
                """.trimIndent(), 
                example = """ 
 import kotlinx.coroutines.* 
 
 fun loadData() { 
     // 在 Activity 或 Fragment 中 
     lifecycleScope.launch(Dispatchers.IO) { 
         val data = fetchFromNetwork() 
         withContext(Dispatchers.Main) { 
             println("Update UI with: $ data") 
         } 
     } 
 } 
 
 suspend fun fetchFromNetwork(): String { 
     delay(1000) 
     return "Network Result" 
 } 
                """.trimIndent() 
            ), 
 
            Lesson( 
                title = "Retrofit + 协程 网络请求", 
                concept = """ 
 Kotlin 协程可以与 Retrofit 无缝结合，用 `suspend` 定义接口方法。  
 使用 `try-catch` 或 `Result` 处理异常更简洁。 
                """.trimIndent(), 
                example = """ 
 interface ApiService { 
     @GET("user/info") 
     suspend fun getUserInfo(): User 
 } 
 
 class UserRepository(private val api: ApiService) { 
     suspend fun fetchUser(): Result<User> { 
         return try { 
             val user = api.getUserInfo() 
             Result.success(user) 
         } catch (e: Exception) { 
             Result.failure(e) 
         } 
     } 
 } 
                """.trimIndent() 
            ), 
 
            Lesson( 
                title = "Room 数据库 + 协程", 
                concept = """ 
 Room 是官方推荐的数据库框架。  
 Kotlin 搭配协程可实现简洁的异步数据库操作。  
 DAO 层可直接使用 `suspend`。 
                """.trimIndent(), 
                example = """ 
 @Entity 
 data class User( 
     @PrimaryKey val id: Int, 
     val name: String 
 ) 
 
 @Dao 
 interface UserDao { 
     @Insert suspend fun insertUser(user: User) 
     @Query("SELECT * FROM User") suspend fun getAll(): List<User> 
 } 
 
 @Database(entities = [User::class], version = 1) 
 abstract class AppDatabase : RoomDatabase() { 
     abstract fun userDao(): UserDao 
 } 
                """.trimIndent() 
            ), 
 
            Lesson( 
                title = "DataStore 保存设置", 
                concept = """ 
 `DataStore` 是 SharedPreferences 的替代方案，用于存储键值数据。  
 分为两种： 
 - `Preferences DataStore`：键值存储； 
 - `Proto DataStore`：结构化存储。 
 支持协程与 Flow。 
                """.trimIndent(), 
                example = """ 
 val Context.dataStore by preferencesDataStore("settings") 
 
 object SettingsKeys { 
     val DARK_MODE = booleanPreferencesKey("dark_mode") 
 } 
 
 suspend fun toggleDarkMode(context: Context) { 
     context.dataStore.edit { prefs -> 
         val current = prefs[SettingsKeys.DARK_MODE] ?: false 
         prefs[SettingsKeys.DARK_MODE] = !current 
     } 
 } 
                """.trimIndent() 
            ), 
 
            Lesson( 
                title = "MVVM 架构示例", 
                concept = """ 
 MVVM 是 Android 推荐的架构。  
 `ViewModel` 负责业务逻辑；`LiveData` 或 `StateFlow` 用于数据通信。  
 Kotlin 协程可与 ViewModelScope 一起使用。 
                """.trimIndent(), 
                example = """ 
 class MainViewModel(private val repo: UserRepository) : ViewModel() { 
 
     private val _user = MutableLiveData<User>() 
     val user: LiveData<User> = _user 
 
     fun loadUser() { 
         viewModelScope.launch { 
             val result = repo.fetchUser() 
             result.onSuccess { _user.value = it } 
         } 
     } 
 } 
                """.trimIndent() 
            ), 
 
            Lesson( 
                title = "Jetpack Compose 实例", 
                concept = """ 
 Jetpack Compose 是 Kotlin 构建 UI 的新方式。  
 声明式编程 + 状态驱动 UI。  
 Compose 天生支持 Kotlin 协程与 Flow。 
                """.trimIndent(), 
                example = """ 
 @Composable 
 fun GreetingScreen(viewModel: MainViewModel) { 
     val user by viewModel.user.observeAsState() 
 
     Column(modifier = Modifier.padding(16.dp)) { 
         Text("Hello ${'$'}{user?.name ?: "Guest"}") 
         Button(onClick = { viewModel.loadUser() }) { 
             Text("Load User") 
         } 
     } 
 } 
                """.trimIndent() 
            ), 
 
            Lesson( 
                title = "项目示例：TodoList", 
                concept = """ 
 一个综合实战项目示例，整合 Kotlin + MVVM + Room + Flow。  
 功能：添加、显示、删除任务。  
 演示 Kotlin 在真实 Android 项目中的完整使用流程。 
                """.trimIndent(), 
                example = """ 
 @Entity 
 data class Todo( 
     @PrimaryKey(autoGenerate = true) val id: Int = 0, 
     val content: String 
 ) 
 
 @Dao 
 interface TodoDao { 
     @Query("SELECT * FROM Todo") fun getAll(): Flow<List<Todo>> 
     @Insert suspend fun insert(todo: Todo) 
     @Delete suspend fun delete(todo: Todo) 
 } 
 
 class TodoRepository(private val dao: TodoDao) { 
     val todos = dao.getAll() 
     suspend fun addTodo(content: String) = dao.insert(Todo(content = content)) 
 } 
 
 class TodoViewModel(private val repo: TodoRepository) : ViewModel() { 
     val todos = repo.todos.asLiveData() 
     fun add(content: String) = viewModelScope.launch { repo.addTodo(content) } 
 } 
                """.trimIndent() 
            ) 
        ) 
        
        // 设置RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = KotlinBasicAdapter(practicalLessons) { lesson ->
            showLessonDetail(lesson)
        }
    }
    
    private fun showLessonDetail(lesson: Lesson) {
        // 创建详情视图
        val detailView = LayoutInflater.from(this).inflate(R.layout.fragment_lesson_detail, null)
        
        // 设置内容
        detailView.findViewById<TextView>(R.id.titleTextView).text = lesson.title
        detailView.findViewById<TextView>(R.id.conceptTextView).text = lesson.concept
        detailView.findViewById<TextView>(R.id.exampleTextView).text = lesson.example
        
        // 创建并显示对话框
        AlertDialog.Builder(this)
            .setView(detailView)
            .setPositiveButton("关闭") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}