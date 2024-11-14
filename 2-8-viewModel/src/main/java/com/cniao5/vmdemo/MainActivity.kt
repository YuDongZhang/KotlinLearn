package com.cniao5.vmdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity() {

    //第一种
    val vmOne by ViewModelLazy<VmOne>(VmOne::class,
        { viewModelStore },
        { defaultViewModelProviderFactory })

    //第二种 ktx
    val vmTwo: VmOne by viewModels<VmOne> { defaultViewModelProviderFactory }

    //android view model
    val vmThree by viewModels<VmTwo> { VmFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //之前版本的
        val vm2 = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(VmOne::class.java)

        //好像已经废弃了
//        ViewModelProvider.NewInstanceFactory()
//        ViewModelProvider.AndroidViewModelFactory(application)
//        ViewModelProvider.Factory

        //输出结果
        Log.i("MainActivity", "vm.getNum ${vmOne.getNum()}")
        Log.i("MainActivity", "vm1.getNum ${vmTwo.getNum()}")
        Log.i("MainActivity", "vm2.getNum ${vm2.getNum()}")
        Log.i("MainActivity", "vmodel2.getNum ${vmThree.getNum()}")

        //点击后新创建  你可以看log来查看他的生命周期的问题 , viewmodel中自动做了数据的操作
        findViewById<Button>(R.id.button).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    suspend fun test(){
        runBlocking {

        }

        GlobalScope.launch {

        }

        coroutineScope {
            launch {

            }
        }

        runBlocking {
            launch {

            }
        }

    }



}

