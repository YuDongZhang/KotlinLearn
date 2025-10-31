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
import com.pdxx.kotlinlearn.databinding.ActivityKotlinBasicThreeBinding

class KotlinBasicThreeActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val binding = DataBindingUtil.setContentView<ActivityKotlinBasicThreeBinding>(
            this, R.layout.activity_kotlin_basic_three
        )
        
        // 创建Kotlin高级特性课程列表
        val advancedLessons = listOf( 
            Lesson( 
                title = "集合（List、Set、Map）", 
                concept = """ 
 Kotlin 提供丰富的集合类型： 
 - `List`：有序集合，可包含重复元素； 
 - `Set`：无序集合，元素唯一； 
 - `Map`：键值对集合。 
 默认集合是不可变的（`listOf`、`setOf`、`mapOf`）， 
 可变集合需使用 `mutableListOf`、`mutableSetOf`、`mutableMapOf`。 
                """.trimIndent(), 
                example = """ 
 fun main() { 
     // 不可变集合 
     val list = listOf("A", "B", "C") 
     val set = setOf(1, 2, 3, 3) 
     val map = mapOf("name" to "Tom", "age" to 20) 
 
     println(list) // [A, B, C] 
     println(set)  // [1, 2, 3] 
     println(map)  // {name=Tom, age=20} 
 
     // 可变集合 
     val mutableList = mutableListOf(1, 2) 
     mutableList.add(3) 
     println(mutableList) // [1, 2, 3] 
 } 
                """.trimIndent() 
            ), 
 
            Lesson( 
                title = "Lambda 表达式与高阶函数", 
                concept = """ 
 Lambda 表达式是 Kotlin 的匿名函数。  
 高阶函数是以函数作为参数或返回值的函数。  
 语法：`{ 参数 -> 函数体 }` 
 常用于集合操作（map、filter、forEach）。 
                """.trimIndent(), 
                example = """ 
 fun main() { 
     val numbers = listOf(1, 2, 3, 4, 5) 
 
     // Lambda 表达式 
     val doubled = numbers.map { it * 2 } 
     println(doubled) // [2, 4, 6, 8, 10] 
 
     // 高阶函数 
     fun operate(a: Int, b: Int, action: (Int, Int) -> Int): Int { 
         return action(a, b) 
     } 
 
     val result = operate(3, 5) { x, y -> x + y } 
     println(result) // 8 
 } 
                """.trimIndent() 
            ), 
 
            Lesson( 
                title = "扩展函数与扩展属性", 
                concept = """ 
 扩展函数可以给已有类"添加新功能"，无需继承。  
 语法：`fun 类名.函数名(参数): 返回类型`  
 扩展属性用于为类添加新的只读或可写属性。 
                """.trimIndent(), 
                example = """ 
 // 扩展函数 
 fun String.lastChar(): Char = this[this.length - 1] 
 
 // 扩展属性 
 val String.wordCount: Int 
     get() = this.split(" ").size 
 
 fun main() { 
     val text = "Hello Kotlin" 
     println(text.lastChar())  // n 
     println(text.wordCount)   // 2 
 } 
                """.trimIndent() 
            ), 
 
            Lesson( 
                title = "空安全（Null Safety）", 
                concept = """ 
 Kotlin 最大的特性之一是"空安全"。  
 通过类型系统避免空指针异常（NPE）。 
 关键符号： 
 - `?`：可空类型； 
 - `!!`：强制非空（可能抛异常）； 
 - `?:`：Elvis 操作符（提供默认值）； 
 - `let`、`run`：安全调用。 
                """.trimIndent(), 
                example = """ 
 fun main() { 
     var name: String? = "Tom" 
     println(name?.length) // 安全调用，输出 3 
 
     name = null 
     println(name?.length ?: 0) // Elvis 操作符，输出 0 
 
     name?.let { 
         println("Name is $ it") 
     } // null 时不会执行 
 } 
                """.trimIndent() 
            ), 
 
            Lesson( 
                title = "泛型（Generics）", 
                concept = """ 
 泛型让类或函数能操作不同类型的数据。  
 语法：`fun <T> functionName(param: T): T`  
 支持协变（out）和逆变（in）修饰符。 
                """.trimIndent(), 
                example = """ 
 // 泛型函数 
 fun <T> echo(value: T): T { 
     return value 
 } 
 
 // 泛型类 
 class Box<T>(var content: T) 
 
 fun main() { 
     println(echo("Hello")) 
     val intBox = Box(123) 
     println(intBox.content) 
 } 
                """.trimIndent() 
            ), 
 
            Lesson( 
                title = "内联函数（inline）", 
                concept = """ 
 内联函数用于减少高阶函数带来的运行时开销。  
 `inline` 会在编译期将函数体直接展开。  
 常用于性能优化或控制作用域函数。 
                """.trimIndent(), 
                example = """ 
 inline fun measureTime(action: () -> Unit) { 
     val start = System.currentTimeMillis() 
     action() 
     val end = System.currentTimeMillis() 
     println("Elapsed: $ {end - start}ms") 
 } 
 
 fun main() { 
     measureTime { 
         Thread.sleep(200) 
         println("Task done!") 
     } 
 } 
                """.trimIndent() 
            ) 
        ) 
        
        // 设置RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = KotlinBasicAdapter(advancedLessons) { lesson ->
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