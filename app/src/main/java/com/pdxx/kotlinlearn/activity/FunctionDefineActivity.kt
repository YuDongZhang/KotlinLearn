package com.pdxx.kotlinlearn.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.pdxx.kotlinlearn.R

class FunctionDefineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_function_define)

        // 获取布局中的 TextView 引用
        val variableExampleTextView: TextView = findViewById(R.id.variableExample)
        val functionExampleTextView: TextView = findViewById(R.id.functionExample)
        val methodExampleTextView: TextView = findViewById(R.id.methodExample)

        // 1. 变量 (Variables)
        // val 用于声明不可变变量 (read-only)，一旦赋值不能再改变
        val immutableVariable: String = "这是一个不可变字符串"
        // var 用于声明可变变量 (mutable)，可以重新赋值
        var mutableVariable: Int = 10
        mutableVariable = 20 // 可以重新赋值

        // 变量示例显示在 UI 上
        variableExampleTextView.text = """
            val immutableVariable: String = "$immutableVariable"
            var mutableVariable: Int = $mutableVariable
            mutableVariable = 30 // 重新赋值
            // mutableVariable 现在的值是: 30
        """.trimIndent()

        // 2. 函数 (Functions)
        // 定义一个没有参数和返回值的函数
        fun sayHello() {
            // 这是一个简单的函数
        }

        // 定义一个带参数和返回值的函数
        fun add(a: Int, b: Int): Int {
            return a + b
        }

        // 调用函数并显示结果
        val sum = add(5, 3)
        functionExampleTextView.text = """
            fun sayHello() {
                // ...
            }

            fun add(a: Int, b: Int): Int {
                return a + b
            }

            val result = add(5, 3) // 调用 add 函数
            // 结果: $sum
        """.trimIndent()

        // 3. 方法 (Methods)
        // 定义一个类
        class Calculator {
            // 在类内部定义的函数称为方法
            fun multiply(x: Int, y: Int): Int {
                return x * y
            }

            fun divide(x: Double, y: Double): Double {
                if (y == 0.0) {
                    throw IllegalArgumentException("除数不能为零")
                }
                return x / y
            }
        }

        // 创建类的实例
        val calculator = Calculator()
        // 调用方法
        val product = calculator.multiply(4, 6)
        val quotient = calculator.divide(10.0, 2.0)

        methodExampleTextView.text = """
            class Calculator {
                fun multiply(x: Int, y: Int): Int {
                    return x * y
                }
            }

            val calculator = Calculator()
            val product = calculator.multiply(4, 6) // 调用 multiply 方法
            // 结果: $product

            val quotient = calculator.divide(10.0, 2.0) // 调用 divide 方法
            // 结果: $quotient
        """.trimIndent()
    }
}
