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
import com.pdxx.kotlinlearn.databinding.ActivityKotlinBasicTwoBinding

class KotlinBasicTwoActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val binding = DataBindingUtil.setContentView<ActivityKotlinBasicTwoBinding>(
            this, R.layout.activity_kotlin_basic_two
        )
        
        // 创建Kotlin面向对象编程课程列表
        val oopLessons = listOf(
            Lesson(
                title = "类与对象",
                concept = """
 在 Kotlin 中，类使用 `class` 关键字定义。
 对象是类的实例，用 `val` 或 `var` 声明。
 构造函数可以写在类名后，也可以定义 `init` 初始化块。
                """.trimIndent(),
                example = """
 class Person(val name: String, var age: Int) {
     init {
         println("Person created: $ name, age $ age")
     }

     fun introduce() {
         println("Hi, I'm $ name and I'm $ age years old.")
     }
 }

 fun main() {
     val p = Person("Tom", 20)
     p.introduce()
 }
                """.trimIndent()
            ),

            Lesson(
                title = "继承",
                concept = """
 Kotlin 的类默认是 `final`，不能被继承。
 如果想让类可以被继承，需要加上 `open`。
 重写函数使用 `override`。
                """.trimIndent(),
                example = """
 open class Animal {
     open fun sound() {
         println("Some sound")
     }
 }

 class Dog : Animal() {
     override fun sound() {
         println("Woof!")
     }
 }

 fun main() {
     val dog = Dog()
     dog.sound() // 输出 Woof!
 }
                """.trimIndent()
            ),

            Lesson(
                title = "接口与抽象类",
                concept = """
 `interface` 用于定义行为规范，可以包含抽象方法和默认实现。
 `abstract class` 可以包含成员变量和具体实现，但不能实例化。
 类可以实现多个接口，但只能继承一个类。
                """.trimIndent(),
                example = """
 interface Flyable {
     fun fly()
 }

 abstract class Bird(val name: String) {
     abstract fun sound()
 }

 class Eagle(name: String) : Bird(name), Flyable {
     override fun fly() {
         println("$ name is flying high!")
     }

     override fun sound() {
         println("$ name screams!")
     }
 }

 fun main() {
     val eagle = Eagle("Eagle")
     eagle.fly()
     eagle.sound()
 }
                """.trimIndent()
            ),

            Lesson(
                title = "可见性修饰符",
                concept = """
 Kotlin 提供四种可见性修饰符控制访问范围：
 - `public`：默认，所有地方可见。
 - `private`：仅在当前文件或类内可见。
 - `protected`：在类和子类中可见。
 - `internal`：在同一模块内可见。
                """.trimIndent(),
                example = """
 open class Parent {
     private val privateData = "private"
     protected val protectedData = "protected"
     internal val internalData = "internal"
     val publicData = "public"
 }

 class Child : Parent() {
     fun show() {
         // println(privateData) // ❌ 无法访问
         println(protectedData)  // ✅ 子类可访问
         println(internalData)   // ✅ 同模块可访问
         println(publicData)     // ✅ 公共可访问
     }
 }

 fun main() {
     val c = Child()
     c.show()
 }
                """.trimIndent()
            ),

            Lesson(
                title = "数据类 (Data Class)",
                concept = """
 `data class` 用于表示数据结构。
 编译器会自动生成常用方法：`toString()`、`equals()`、`hashCode()`、`copy()`。
                """.trimIndent(),
                example = """
 data class User(val name: String, val age: Int)

 fun main() {
     val user1 = User("Alice", 18)
     val user2 = user1.copy(age = 20)

     println(user1) // 输出：User(name=Alice, age=18)
     println(user2) // 输出：User(name=Alice, age=20)
     println(user1 == user2) // false
 }
                """.trimIndent()
            ),

            Lesson(
                title = "对象声明与伴生对象",
                concept = """
 `object` 用于声明单例对象。
 `companion object` 用于定义类的静态成员（类似 Java 的 static）。
                """.trimIndent(),
                example = """
 object Logger {
     fun log(message: String) {
         println("[LOG] $ message")
     }
 }

 class MathUtil {
     companion object {
         fun add(a: Int, b: Int): Int = a + b
     }
 }

 fun main() {
     Logger.log("App started")
     println(MathUtil.add(3, 5))
 }
                """.trimIndent()
            )
        )
        
        // 设置RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = KotlinBasicAdapter(oopLessons) { lesson ->
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