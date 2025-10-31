package com.pdxx.kotlinlearn.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.adapter.KotlinBasicAdapter
import com.pdxx.kotlinlearn.data.Lesson
import com.pdxx.kotlinlearn.databinding.ActivityKotlinBasicTwoBinding
import com.pdxx.kotlinlearn.utils.JsonDataLoader

class KotlinBasicTwoActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val binding = DataBindingUtil.setContentView<ActivityKotlinBasicTwoBinding>(
            this, R.layout.activity_kotlin_basic_two
        )
        
        // 从JSON文件加载Kotlin面向对象编程课程列表
        val oopLessons = JsonDataLoader.loadLessonsFromAssets(this, "kotlin_basic_two_lessons.json")
        
        // 设置RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = KotlinBasicAdapter(oopLessons) { lesson ->
            showLessonDetail(lesson)
        }
    }
    
    private fun showLessonDetail(lesson: Lesson) {
        // 创建详情视图
        val detailView = LayoutInflater.from(this).inflate(R.layout.fragment_lesson_detail, null)
        
        // 设置标题
        detailView.findViewById<TextView>(R.id.titleTextView).text = lesson.title
        
        // 获取内容容器
        val contentContainer = detailView.findViewById<LinearLayout>(R.id.contentContainer)
        
        // 遍历lesson.list数组，为每个概念和示例创建视图
        lesson.list.forEachIndexed { index, lessonItem ->
            // 创建概念标题
            val conceptTitle = TextView(this).apply {
                text = "概念 ${index + 1}"
                textSize = 16f
                setTypeface(null, android.graphics.Typeface.BOLD)
                setPadding(0, 24, 0, 8)
            }
            contentContainer.addView(conceptTitle)
            
            // 创建概念内容
            val conceptText = TextView(this).apply {
                text = lessonItem.concept
                textSize = 14f
                setPadding(0, 0, 0, 16)
            }
            contentContainer.addView(conceptText)
            
            // 创建示例标题
            val exampleTitle = TextView(this).apply {
                text = "示例 ${index + 1}"
                textSize = 16f
                setTypeface(null, android.graphics.Typeface.BOLD)
                setPadding(0, 8, 0, 8)
            }
            contentContainer.addView(exampleTitle)
            
            // 创建示例内容
            val exampleText = TextView(this).apply {
                text = lessonItem.example
                textSize = 14f
                typeface = android.graphics.Typeface.MONOSPACE
                setPadding(0, 0, 0, 24)
            }
            contentContainer.addView(exampleText)
        }
        
        // 创建并显示对话框
        AlertDialog.Builder(this)
            .setView(detailView)
            .setPositiveButton("关闭") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}