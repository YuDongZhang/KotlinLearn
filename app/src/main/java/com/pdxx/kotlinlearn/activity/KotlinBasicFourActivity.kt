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
import com.pdxx.kotlinlearn.databinding.ActivityKotlinBasicFourBinding

class KotlinBasicFourActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val binding = DataBindingUtil.setContentView<ActivityKotlinBasicFourBinding>(
            this, R.layout.activity_kotlin_basic_four
        )
        
        // 创建Kotlin高级应用课程列表
        val advancedAppLessons = listOf( 
            Lesson( 
                title = "协程（Coroutines）", 
                concept = """ 
 协程是 Kotlin 处理异步任务的核心机制。  
 相比传统线程更轻量，可通过 `launch`、`async` 等方式并发执行任务。  
 需要依赖 `kotlinx.coroutines` 库。  
 核心概念： 
 - `CoroutineScope`：协程作用域 
 - `launch`：启动不返回结果的协程 
 - `async/await`：启动并返回结果的协程 
 - `suspend`：定义挂起函数，可在协程中挂起或恢复执行 
                """.trimIndent(), 
                example = """ 
 import kotlinx.coroutines.* 
 
 suspend fun fetchData(): String { 
     delay(1000) // 模拟耗时任务 
     return "Data Loaded" 
 } 
 
 fun main() = runBlocking { 
     launch { 
         println("Start loading...") 
         val data = fetchData() 
         println(data) 
     } 
     println("Main continues...") 
 } 
                """.trimIndent() 
            ), 
 
            Lesson( 
                title = "异步流（Flow）", 
                concept = """ 
 `Flow` 是 Kotlin 协程中的异步数据流，类似于 RxJava 的 Observable。  
 特点： 
 - 顺序、冷流（只有被收集时才执行） 
 - 使用 `emit()` 发送数据，用 `collect()` 接收数据  
 - 常见操作符：`map`、`filter`、`take`、`collectLatest` 
                """.trimIndent(), 
                example = """ 
 import kotlinx.coroutines.* 
 import kotlinx.coroutines.flow.* 
 
 fun numbers(): Flow<Int> = flow { 
     for (i in 1..3) { 
         delay(500) 
         emit(i) 
     } 
 } 
 
 fun main() = runBlocking { 
     numbers() 
         .map { it * it } 
         .collect { value -> 
             println("Received: $ value") 
         } 
 } 
                """.trimIndent() 
            ), 
 
            Lesson( 
                title = "Kotlin 与 Java 互操作", 
                concept = """ 
 Kotlin 与 Java 可以无缝互相调用。  
 通过注解可以优化互操作体验： 
 - `@JvmStatic`：在 Java 中以静态方法调用； 
 - `@JvmOverloads`：为默认参数生成多个重载； 
 - `@JvmName`：修改生成的 Java 方法名； 
 - `@Throws`：声明可能抛出的异常。 
                """.trimIndent(), 
                example = """ 
 class Utils { 
     companion object { 
         @JvmStatic 
         fun sayHello(name: String = "Guest") { 
             println("Hello, $ name!") 
         } 
 
         @JvmOverloads 
         fun sum(a: Int, b: Int = 0) = a + b 
     } 
 } 
 
 // Java 调用： 
 // Utils.sayHello("Tom"); 
 // Utils.sum(3); 
 fun main() { 
     Utils.sayHello("Tom") 
     println(Utils.sum(5)) 
 } 
                """.trimIndent() 
            ), 
 
            Lesson( 
                title = "Kotlin DSL", 
                concept = """ 
 DSL（领域特定语言）是一种让代码更接近自然语言的语法风格。  
 Kotlin 支持编写自己的 DSL，例如 Gradle Kotlin DSL（`build.gradle.kts`）。  
 核心技术：高阶函数 + Lambda + 接收者函数。 
                """.trimIndent(), 
                example = """ 
 class Robot { 
     fun move(direction: String) = println("Move $ direction") 
     fun speak(message: String) = println("Say: $ message") 
 } 
 
 fun robot(block: Robot.() -> Unit) { 
     val r = Robot() 
     r.block() 
 } 
 
 fun main() { 
     robot { 
         move("forward") 
         speak("Hello Kotlin DSL!") 
     } 
 } 
                """.trimIndent() 
            ), 
 
            Lesson( 
                title = "反射（Reflection）", 
                concept = """ 
 Kotlin 的反射机制可以在运行时动态访问类、函数和属性信息。  
 主要入口是 `KClass`。  
 常见用法： 
 - 获取类信息：`obj::class` 
 - 获取属性：`memberProperties` 
 - 获取函数：`memberFunctions` 
                """.trimIndent(), 
                example = """ 
 import kotlin.reflect.full.* 
 
 data class User(val name: String, val age: Int) 
 
 fun main() { 
     val user = User("Alice", 18) 
     val kClass = user::class 
 
     println("类名: $ {kClass.simpleName}") 
     println("属性:") 
     kClass.memberProperties.forEach { println(it.name) } 
 } 
                """.trimIndent() 
            ), 
 
            Lesson( 
                title = "注解与 KSP（Kotlin Symbol Processing）", 
                concept = """ 
 注解（Annotation）用于在代码中添加元信息。  
 Kotlin 的 KSP 是编译时处理注解的工具，比 KAPT 更快。  
 常见注解：`@Deprecated`、`@JvmStatic`、`@Retention`、`@Target` 等。  
 自定义注解可用于自动生成代码或校验。 
                """.trimIndent(), 
                example = """ 
 @Target(AnnotationTarget.CLASS) 
 @Retention(AnnotationRetention.RUNTIME) 
 annotation class AutoLog(val message: String) 
 
 @AutoLog("This is a test class") 
 class TestClass 
 
 fun main() { 
     val clazz = TestClass::class 
     val annotation = clazz.annotations.find { it is AutoLog } as? AutoLog 
     println(annotation?.message) 
 } 
                """.trimIndent() 
            ) 
        ) 
        
        // 设置RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = KotlinBasicAdapter(advancedAppLessons) { lesson ->
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