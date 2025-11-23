package com.pdxx.kotlinlearn.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.bean.ListItem
import com.pdxx.kotlinlearn.custom.CustomActivity
import com.pdxx.kotlinlearn.databind.MainDBActivity
import com.pdxx.kotlinlearn.databinding.ActivityMainBinding
import com.pdxx.kotlinlearn.exception.ListExceptionActivity
import com.pdxx.kotlinlearn.koindemo.ui.KoinActivity
import com.pdxx.kotlinlearn.ktxdemo.KtxDemoActivity
import com.pdxx.kotlinlearn.moduleFunny.PagingActivity
import com.pdxx.kotlinlearn.mvi.MviActivity
import kotlin.random.Random

class AndroidPracticeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val items = mutableListOf<ListItem>()

        items.add(ListItem("1 .函数定义", getRandomColor(), FunctionDefineActivity::class.java))
        items.add(ListItem("2 .recyclerview", getRandomColor(), ListActivity::class.java))
        items.add(ListItem("3 .fragment", getRandomColor(), SwitchFragmentActivity::class.java))
        items.add(ListItem("4 .ViewPager翻页", getRandomColor(), ViewPagerActivity::class.java))
        items.add(ListItem("5 .普通的写的方式", getRandomColor(), GeneralActivity::class.java))
        items.add(ListItem("6 .navigation", getRandomColor(), NavigationActivity::class.java))
        items.add(ListItem("7 .paging", getRandomColor(), PagingActivity::class.java))
        items.add(ListItem("8 .协程", getRandomColor(), CoroutinesActivity::class.java))
        items.add(ListItem("9 .mvi架构", getRandomColor(), MviActivity::class.java))
        items.add(ListItem("10 .LiveDate讲解", getRandomColor(), LiveActivity::class.java))
        items.add(ListItem("11 .mvvm讲解", getRandomColor(), MvvmOneActivity::class.java))
        items.add(ListItem("12. Scope Functions 符号", getRandomColor(), ScopeFunctionsActivity::class.java))
        items.add(ListItem("13. databind使用", getRandomColor(), MainDBActivity::class.java))
        items.add(ListItem("14. Lifecycle学习", getRandomColor(), LifecycleActivity::class.java))
        items.add(ListItem("15. Koin MVVM Demo", getRandomColor(), KoinActivity::class.java))
        items.add(ListItem("16. 自定义控件", getRandomColor(), CustomActivity::class.java))
        items.add(ListItem("17. KTX MVVM Demo", getRandomColor(), KtxDemoActivity::class.java))
        items.add(ListItem("18. 内存的泄漏", getRandomColor(), ListExceptionActivity::class.java))

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = ItemListAdapter(items)
    }

    private fun getRandomColor(): Int {
        val rnd = Random
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }
}