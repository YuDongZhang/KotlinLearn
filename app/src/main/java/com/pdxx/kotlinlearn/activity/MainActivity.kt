package com.pdxx.kotlinlearn.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.bean.ListItem
import com.pdxx.kotlinlearn.bean.PersonEntity
import com.pdxx.kotlinlearn.bean.Student
import com.pdxx.kotlinlearn.custom.CustomActivity
import com.pdxx.kotlinlearn.databind.MainDBActivity
import com.pdxx.kotlinlearn.databinding.ActivityMainBinding
import com.pdxx.kotlinlearn.exception.ListExceptionActivity
import com.pdxx.kotlinlearn.koindemo.ui.KoinActivity
import com.pdxx.kotlinlearn.ktxdemo.KtxDemoActivity

import com.pdxx.kotlinlearn.moduleFunny.PagingActivity
import com.pdxx.kotlinlearn.mvi.MviActivity
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.qualifier.qualifier
import kotlin.random.Random



class MainActivity : AppCompatActivity() {

    lateinit var tvContent: TextView

    val p: PersonEntity by inject<PersonEntity>()
    val p2: PersonEntity = get()
    val s1 = get<Student>(named("name"))
    val s2 = get<Student>(qualifier<PersonEntity>())
    var tv: TextView? = null
    val viewInfo: ViewInfo by inject<ViewInfo> { parametersOf(tv, tvContent) }

    private fun testLog() {
        Log.d("testLog: ", "" + p.hashCode())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 设置状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val items = mutableListOf<ListItem>()

        items.add(ListItem("1 .kotlin基础知识一", getRandomColor(), KotlinBasicOneActivity::class.java))
        items.add(ListItem("2 .kotlin基础知识二", getRandomColor(), KotlinBasicTwoActivity::class.java))
        items.add(ListItem("3 .Android基础知识一", getRandomColor(), AndroidBasicOneActivity::class.java))
        items.add(ListItem("3 .Android基础知识二", getRandomColor(), AndroidBasicTwoActivity::class.java))
        items.add(ListItem("4 .Android实践", getRandomColor(), AndroidPracticeActivity::class.java))


        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = ItemListAdapter(items)

        Log.d("TAG", "onCreate: " + p2.company)

        val leakThread = LeakThread()
        leakThread.start()
    }

    private fun getRandomColor(): Int {
        val rnd = Random
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    inner class LeakThread : Thread() {
        override fun run() {
            super.run()
            try {
                sleep(6 * 60 * 1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}

class ViewInfo(val view: View) : KoinComponent {

}