package com.pdxx.kotlinlearn.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.bean.ListItem
import com.pdxx.kotlinlearn.bean.PersonEntity
import com.pdxx.kotlinlearn.bean.Student
import com.pdxx.kotlinlearn.databinding.ActivityMainBinding
import com.pdxx.kotlinlearn.moduleFunny.PagingActivity
import com.pdxx.kotlinlearn.mvi.MviActivity
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.qualifier.qualifier
import kotlin.math.log
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

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val items = mutableListOf<ListItem>()

        // Extract data from existing TextViews and their click listeners
        // I'm hardcoding the class names here based on the original code.
        // In a real project, you might have a more dynamic way to map these.
        items.add(ListItem("1 .函数定义", getRandomColor(), FunctionDefineActivity::class.java))
        items.add(ListItem("2 .recyclerview", getRandomColor(), ListActivity::class.java))
        items.add(ListItem("3 .fragment", getRandomColor(), SwitchFragmentActivity::class.java))
        items.add(ListItem("4 .ViewPager翻页", getRandomColor(), ViewPagerActivity::class.java))
        items.add(ListItem("5 .普通的写的方式", getRandomColor(), GeneralActivity::class.java))
        items.add(ListItem("6 .navigation", getRandomColor(), NavigationActivity::class.java))
        items.add(ListItem("7 .paging", getRandomColor(), PagingActivity::class.java))
        items.add(ListItem("8 .协程", getRandomColor(), CoroutinesActivity::class.java))
        items.add(ListItem("9 .mvi架构", getRandomColor(), MviActivity::class.java))



        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = ItemListAdapter(items)

        Log.d("TAG", "onCreate: " + p2.company)
    }

    private fun getRandomColor(): Int {
        val rnd = Random
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }
}

class ViewInfo(val view: View) : KoinComponent {

}