package com.pdxx.kotlinlearn.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.bean.PersonEntity
import com.pdxx.kotlinlearn.bean.Student
import com.pdxx.kotlinlearn.bean.ViewInfo
import com.pdxx.kotlinlearn.databinding.ActivityMainBinding
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.qualifier.qualifier


class MainActivity : AppCompatActivity() {

    // @BindView(R.id.content)
    lateinit var tvContent: TextView
    //  var unbinder: Unbinder? = null

    val p :PersonEntity by inject<PersonEntity> () //lazy 模式

    val p2 : PersonEntity = get()

    val s1 = get<Student>(named("name"))

    val s2= get<Student>(qualifier<PersonEntity>())

    //外部参数
    var tv :TextView ?=null
    val viewInfo:ViewInfo by inject<ViewInfo> { parametersOf(tv) }//parametersOf可接多个参数


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
        Log.d("TAG", "onCreate: "+p2.company)
    }

}
