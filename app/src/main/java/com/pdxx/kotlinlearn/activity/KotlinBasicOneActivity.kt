package com.pdxx.kotlinlearn.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.adapter.KotlinBasicAdapter
import com.pdxx.kotlinlearn.data.Lesson
import com.pdxx.kotlinlearn.databinding.ActivityKotlinBasicOneBinding
import com.pdxx.kotlinlearn.utils.JsonDataLoader

class KotlinBasicOneActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val binding = DataBindingUtil.setContentView<ActivityKotlinBasicOneBinding>(
            this, R.layout.activity_kotlin_basic_one
        )
        
        // 从JSON文件加载课程数据
        val basicLessons = JsonDataLoader.loadLessonsFromAssets(this, "kotlin_basic_one_lessons.json")
        
        // 设置RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = KotlinBasicAdapter(basicLessons) { lesson ->
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
        
        // 为每个概念和例子创建视图
        lesson.list.forEachIndexed { index, lessonItem ->
            // 概念标签
            val conceptLabel = TextView(this).apply {
                text = "概念说明 ${index + 1}:"
                textSize = 18f
                setTypeface(null, android.graphics.Typeface.BOLD)
                setTextColor(resources.getColor(android.R.color.holo_blue_dark, null))
                setPadding(0, 24, 0, 8)
            }
            contentContainer.addView(conceptLabel)
            
            // 概念内容
            val conceptText = TextView(this).apply {
                text = lessonItem.concept
                textSize = 16f
                setTextColor(resources.getColor(android.R.color.black, null))
                setPadding(0, 0, 0, 16)
            }
            contentContainer.addView(conceptText)
            
            // 示例标签
            val exampleLabel = TextView(this).apply {
                text = "示例代码 ${index + 1}:"
                textSize = 18f
                setTypeface(null, android.graphics.Typeface.BOLD)
                setTextColor(resources.getColor(android.R.color.holo_blue_dark, null))
                setPadding(0, 0, 0, 8)
            }
            contentContainer.addView(exampleLabel)
            
            // 示例代码
            val exampleText = TextView(this).apply {
                text = lessonItem.example
                textSize = 14f
                typeface = android.graphics.Typeface.MONOSPACE
                setBackgroundColor(resources.getColor(android.R.color.background_light, null))
                setPadding(48, 48, 48, 48)
                setTextColor(resources.getColor(android.R.color.black, null))
                setPadding(12, 12, 12, 12)
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