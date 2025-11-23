package com.pdxx.kotlinlearn.activity

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.adapter.KotlinBasicAdapter
import com.pdxx.kotlinlearn.data.Lesson
import com.pdxx.kotlinlearn.databinding.ActivityKotlinBasicThreeBinding
import com.pdxx.kotlinlearn.databinding.FragmentLessonDetailBinding
import com.pdxx.kotlinlearn.utils.JsonDataLoader

class KotlinBasicThreeActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val binding = DataBindingUtil.setContentView<ActivityKotlinBasicThreeBinding>(
            this, R.layout.activity_kotlin_basic_three
        )
        
        // 从JSON文件加载Kotlin高级特性课程列表
        val advancedLessons = JsonDataLoader.loadLessonsFromAssets(this, "kotlin_basic_three_lessons.json")
        
        // 设置RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = KotlinBasicAdapter(advancedLessons) { lesson ->
            showLessonDetail(lesson)
        }
    }
    
    private fun showLessonDetail(lesson: Lesson) {
        // 使用DataBinding创建详情视图
        val binding = FragmentLessonDetailBinding.inflate(LayoutInflater.from(this))
        
        // 设置标题
        binding.titleTextView.text = lesson.title
        
        // 清空原有内容
        binding.contentContainer.removeAllViews()
        
        // 遍历lesson.list数组，为每个LessonItem创建视图
        lesson.list?.forEachIndexed { index, lessonItem ->
            // 概念标题
            val conceptTitleTextView = TextView(this).apply {
                text = "概念 ${index + 1}"
                setTypeface(null, Typeface.BOLD)
                setTextColor(Color.BLACK)
                textSize = 16f
                setPadding(0, 24, 0, 8)
            }
            binding.contentContainer.addView(conceptTitleTextView)
            
            // 概念内容
            val conceptTextView = TextView(this).apply {
                text = lessonItem.concept
                setTextColor(Color.DKGRAY)
                textSize = 14f
                setPadding(0, 0, 0, 16)
            }
            binding.contentContainer.addView(conceptTextView)
            
            // 示例标题
            val exampleTitleTextView = TextView(this).apply {
                text = "示例 ${index + 1}"
                setTypeface(null, Typeface.BOLD)
                setTextColor(Color.BLACK)
                textSize = 16f
                setPadding(0, 8, 0, 8)
            }
            binding.contentContainer.addView(exampleTitleTextView)
            
            // 示例代码
            val exampleTextView = TextView(this).apply {
                text = lessonItem.example
                setTextColor(Color.DKGRAY)
                textSize = 14f
                setPadding(0, 0, 0, 24)
                // 设置等宽字体以便更好地显示代码
                typeface = Typeface.MONOSPACE
                setBackgroundColor(Color.parseColor("#F5F5F5"))
                setPadding(16, 16, 16, 16)
            }
            binding.contentContainer.addView(exampleTextView)
        }
        
        // 创建并显示对话框
        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setView(binding.root)
            .setPositiveButton("关闭") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        
        // 显示对话框，并设置宽度为屏幕宽度的90%
        dialog.show()
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}