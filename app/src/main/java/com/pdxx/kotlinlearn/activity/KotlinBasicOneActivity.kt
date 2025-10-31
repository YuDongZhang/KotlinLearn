package com.pdxx.kotlinlearn.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.adapter.KotlinBasicAdapter
import com.pdxx.kotlinlearn.data.Lesson
import com.pdxx.kotlinlearn.databinding.ActivityKotlinBasicOneBinding

class KotlinBasicOneActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val binding = DataBindingUtil.setContentView<ActivityKotlinBasicOneBinding>(
            this, R.layout.activity_kotlin_basic_one
        )
        
        // 创建Kotlin基础知识列表
        val basicLessons = listOf(
            Lesson(
                title = "基本语法",
                concept = """
 Kotlin 程序的入口是 `main` 函数，所有可执行代码从这里开始运行。
 `println()` 用于输出内容。
 注释使用 `//` 表示单行注释，`/* ... */` 表示多行注释。
                """.trimIndent(),
                example = """
 // 这是单行注释
 /*
   这是多行注释
 */

 fun main() {
     println("Hello, Kotlin!") // 输出 Hello, Kotlin!
 }
                """.trimIndent()
            ),

            Lesson(
                title = "变量与常量",
                concept = """
 `val` 用于声明不可变变量（常量），`var` 用于声明可变变量。
 Kotlin 支持类型推断，不一定要写出类型。
                """.trimIndent(),
                example = """
 fun main() {
     val name = "Tom"    // 不可变变量
     var age = 20        // 可变变量
     println("Name: $ name, Age: $ age")

     age = 21            // ✅ OK
     // name = "Jerry"   // ❌ 错误，val 不可修改
 }
                """.trimIndent()
            ),

            Lesson(
                title = "数据类型",
                concept = """
 Kotlin 是强类型语言。常见数据类型：
 - 整数：Byte、Short、Int、Long
 - 浮点：Float、Double
 - 字符：Char
 - 布尔：Boolean
 - 字符串：String
                """.trimIndent(),
                example = """
 fun main() {
     val a: Int = 10
     val b: Double = 3.14
     val c: Boolean = true
     val d: Char = 'K'
     val e: String = "Kotlin"

     println("$ a, $ b, $ c, $ d, $ e")
 }
                """.trimIndent()
            ),

            Lesson(
                title = "运算符",
                concept = """
 Kotlin 支持算术、逻辑、比较和区间运算符：
 - 算术：+ - * / %
 - 比较：== != > < >= <=
 - 逻辑：&& || !
 - 区间：1..5 表示从 1 到 5 的范围
                """.trimIndent(),
                example = """
 fun main() {
     val x = 5
     val y = 3

     println(x + y)       // 8
     println(x > y)       // true
     println(x in 1..10)  // true
     println(x % 2 == 1 && y % 2 == 1) // true
 }
                """.trimIndent()
            ),

            Lesson(
                title = "控制流",
                concept = """
 控制语句决定程序执行的路径：
 - if 表达式
 - when 表达式
 - for 循环
 - while 循环
                """.trimIndent(),
                example = """
 fun main() {
     val score = 85

     // if 表达式
     val grade = if (score >= 90) "A" else if (score >= 80) "B" else "C"
     println(grade) // 输出 B

     // when 表达式
     when (grade) {
         "A" -> println("Excellent")
         "B" -> println("Good")
         "C" -> println("Pass")
         else -> println("Unknown")
     }

     // for 循环
     for (i in 1..3) {
         println("Count: $ i")
     }

     // while 循环
     var num = 0
     while (num < 3) {
         println("num = $ num")
         num++
     }
 }
                """.trimIndent()
            ),

            Lesson(
                title = "函数",
                concept = """
 函数使用 `fun` 关键字定义。
 可以有参数和返回值。
 `Unit` 表示没有返回值（类似 Java 的 void）。
 支持默认参数与命名参数。
                """.trimIndent(),
                example = """
 // 带参数和返回值
 fun add(a: Int, b: Int): Int {
     return a + b
 }

 // 默认参数与命名参数
 fun greet(name: String = "Guest") {
     println("Hello, $ name!")
 }

 fun main() {
     println(add(3, 5))  // 输出 8
     greet()             // 使用默认参数
     greet(name = "Tom") // 使用命名参数
 }
                """.trimIndent()
            )
        )
        
        // 设置RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = KotlinBasicAdapter(basicLessons) { lesson ->
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