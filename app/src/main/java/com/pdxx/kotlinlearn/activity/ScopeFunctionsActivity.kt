package com.pdxx.kotlinlearn.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.pdxx.kotlinlearn.databinding.ActivityScopeFunctionsBinding

class ScopeFunctionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScopeFunctionsBinding
    private val TAG = "ScopeFunctions"

    // 用于示例的简单数据类
    data class Person(var name: String, var age: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScopeFunctionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupApplyExample()
        setupAlsoExample()
        setupLetExample()
        setupRunExample()
        setupWithExample()
    }

    private fun setupApplyExample() {
        binding.btnApply.setOnClickListener {
            // 示例: 创建一个TextView, 配置其属性, 然后添加到布局中。
            val newTextView = TextView(this).apply {
                // 在 apply 内部, `this` 指向 TextView 本身。
                // 非常适合用于对象初始化。
                text = "我是用 apply 创建的 TextView"
                textSize = 16f
                setTextColor(Color.BLUE)
                // `apply` 返回上下文对象本身 (即这个TextView)。
            }
            // 为了演示, 我们只在结果TextView中显示它的文本。
            binding.tvApplyResult.text = "一个新的TextView被创建并配置了文本: '${newTextView.text}'"
        }
    }

    private fun setupAlsoExample() {
        binding.btnAlso.setOnClickListener {
            // 示例: 创建一个对象并执行一个副作用操作 (如打印日志), 而不改变对象本身。
            val person = Person("爱丽丝", 30).also {
                // 在 also 内部, 上下文对象是 `it`。
                // 它对于不修改对象本身的操作很有用, 例如日志记录或添加到列表中。
                Log.d(TAG, "创建了新的人物: ${it.name}, 年龄 ${it.age}")
                // `also` 返回原始对象 (这个Person实例)。
            }
            binding.tvAlsoResult.text = "创建了 ${person.name} 并记录了创建日志。请在Logcat中查看副作用。"
        }
    }

    private fun setupLetExample() {
        binding.btnLet.setOnClickListener {
            // 示例: 安全地处理一个可空变量。
            val nullableName: String? = listOf("鲍勃", null).random()

            val result = nullableName?.let {
                // 这个代码块只在 nullableName 不为 null 时执行。
                // 上下文对象是 `it`。
                // `let` 非常适合进行空安全检查。
                "名字是 ${it}。 长度: ${it.length}"
                // lambda表达式的最后一行是返回值。
            } ?: "名字是 null。"

            binding.tvLetResult.text = result
        }
    }

    private fun setupRunExample() {
        binding.btnRun.setOnClickListener {
            // 示例: 配置一个对象并计算一个结果。
            val person = Person("查理", 25)

            val description = person.run {
                // 在 run 内部, `this` 指向 Person 对象。
                // 它就像 `with` 和 `let` 的结合体。
                // 当你需要对一个对象进行操作并计算出一个结果时, 它很好用。
                age += 1
                "${name} 现在 ${age} 岁了。"
                // 最后一行表达式是返回值。
            }

            binding.tvRunResult.text = description
        }
    }

    private fun setupWithExample() {
        binding.btnWith.setOnClickListener {
            // 示例: 对一个对象进行分组调用。
            val person = Person("戴安娜", 40)

            // `with` 不是一个扩展函数, 所以对象是作为参数传入的。
            val result = with(person) {
                // 在 with 内部, `this` 指向 Person 对象。
                // 当你需要在不形成链式调用的情况下对同一个对象执行多个操作时很有用。
                name = "戴安娜王妃"
                age = 41
                "最终状态: ${this.name}, 年龄: ${this.age}"
            }

            binding.tvWithResult.text = result
        }
    }
}
