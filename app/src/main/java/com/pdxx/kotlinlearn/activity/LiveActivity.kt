package com.pdxx.kotlinlearn.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.databinding.ActivityLiveDataBinding
import com.pdxx.kotlinlearn.frag.AppleFragment

class LiveActivity : AppCompatActivity() {
    //声明变量live data
    val liveAppleData = MutableLiveData<String>()

    //中介者MediatorLiveData使用演示：
    val liveOne = MutableLiveData<String>()
    val liveTwo = MutableLiveData<String>()

    val mediatorLive = MediatorLiveData<Pair<String, String>>()

    private lateinit var binding: ActivityLiveDataBinding//要和名字结合的

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiveDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Fragment
        val appleFragment = AppleFragment()

        supportFragmentManager.beginTransaction()
            .add(R.id.fl_container_live, appleFragment)
            .commit()
        // TODO 注意 hide 和 show不会改变fragment的生命周期状态 所以用attach detach
        //显示fragment
        binding.btnCreateFgLive.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .attach(appleFragment)
                .commit()
            Log.d("LiveActivity", "onCreate: 显示fg ${appleFragment.isVisible}")
        }
        //隐藏
        binding.btnDestroyFgLive.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .detach(appleFragment)
                .commit()
            Log.w("LiveActivity", "onCreate: 隐藏fg ${appleFragment.isVisible}")

        }
        //变更livedata的值
        binding.btnChangeLive.setOnClickListener {
            liveAppleData.value = "当前liveData的值：${System.currentTimeMillis()}"
        }

        //观察值
        liveAppleData.observe(this, Observer {
            binding.tvLiveDataActivity.text = it
            Log.d("LiveActivity", "LiveData在LiveActivity中 $it")
        })

        //map转换后的数值：
        val liveMappedData = liveAppleData.map {
            Pair<Int, String>(it.hashCode(), it)
        }

        liveMappedData.observe(this, Observer {
            binding.tvMappedDataActivity.text = "map 后的值：${it}"
            Log.i("LiveActivity", "LiveData在LiveActivity中 map 后 $it")
        })

        //中介者MediatorLiveData使用演示：
        binding.btnChange1Live.setOnClickListener {
            liveOne.value = "one:${System.currentTimeMillis().toString().takeLast(6)}"
        }
        binding.btnChange2Live.setOnClickListener {
            liveTwo.value = "two:${System.currentTimeMillis().toString().takeLast(6)}"
        }

        mediatorLive.addSource(liveTwo) {
            Log.d("LiveActivity", "LiveActivity中 LiveTwo ---> $it")
            mediatorLive.value = "two >>>>>" to it
        }

        mediatorLive.addSource(liveOne) {
            Log.d("LiveActivity", "LiveActivity中 LiveOne ---> $it")
            mediatorLive.value = "one >>" to it
        }

        mediatorLive.observe(this, Observer {
            Log.w("LiveActivity", "LiveActivity中 mediatorLive ---> $it")
        })
    }
}