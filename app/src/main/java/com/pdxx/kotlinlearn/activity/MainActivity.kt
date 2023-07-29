package com.pdxx.kotlinlearn.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.bean.PersonEntity
import com.pdxx.kotlinlearn.bean.Student
import com.pdxx.kotlinlearn.databinding.ActivityMainBinding
import com.pdxx.kotlinlearn.moduleFunny.PagingActivity
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.qualifier.qualifier
import kotlin.math.log


class MainActivity : AppCompatActivity() {

    // @BindView(R.id.content)
    lateinit var tvContent: TextView
    //  var unbinder: Unbinder? = null

    val p: PersonEntity by inject<PersonEntity>() //lazy 模式

    val p2: PersonEntity = get()

    val s1 = get<Student>(named("name"))

    //在使用的指明标记符
    val s2 = get<Student>(qualifier<PersonEntity>())

    var tv: TextView? = null //注意懒加载问题 ,如果都是 懒 可能为空
    val viewInfo: ViewInfo by inject<ViewInfo> { parametersOf(tv, tvContent) }

    private fun testLog() {
        Log.d("testLog: ", "" + p.hashCode())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        //  setContentView(R.layout.activity_main)
        binding.tv1.setOnClickListener {

        }
        binding.tv2.setOnClickListener {
            var intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }
        binding.tv3.setOnClickListener {
            var intent = Intent(this, SwitchFragmentActivity::class.java)
            startActivity(intent)
        }
        binding.tv4.setOnClickListener {
            var intent = Intent(this, ViewPagerActivity::class.java)
            startActivity(intent)
        }
        binding.tv5.setOnClickListener {
            var intent = Intent(this, GeneralActivity::class.java)
            startActivity(intent)
        }
        binding.tv6.setOnClickListener {
            var intent = Intent(this, NavigationActivity::class.java)
            startActivity(intent)
        }
        binding.apply {
            tv7.setOnClickListener {
                var intent = Intent(this@MainActivity, PagingActivity::class.java)
                startActivity(intent)
            }
            tv8.setOnClickListener {
                var intent = Intent(this@MainActivity, CoroutinesActivity::class.java)
                startActivity(intent)
            }

        }
        Log.d("TAG", "onCreate: " + p2.company)
    }

}

//假如说这个参数不在我们项目之类 , 怎么进行注入
class ViewInfo(val view: View) : KoinComponent {

}