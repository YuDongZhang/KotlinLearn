package com.pdxx.kotlinlearn.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    // @BindView(R.id.content)
    lateinit var tvContent: TextView
    //  var unbinder: Unbinder? = null

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

    }

}
