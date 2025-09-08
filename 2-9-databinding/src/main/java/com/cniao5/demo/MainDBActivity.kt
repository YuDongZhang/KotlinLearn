package com.cniao5.demo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import com.cniao5.demo.databinding.ActivityMainBinding

class MainDBActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //这个地方就是 DataBinding 在 activity 中使用
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        //经过测试布局中是不能加注释的
        //赋值    apply对这个作用对象,对他做一系列的操作 , 对binding做一系列的操作
        binding.apply {

            //这里这些参数都是直接通向界面的
            name = null

            address = "Beijing 海淀"

            obName = ObservableField<String>("原始的obName")

            user = User()//普通user
            fUser = FoUser()//个别属性binding
            oUser = ObUser()

            click = View.OnClickListener {
                address = "菜鸟窝！！"
                user?.name = "普通user改name？"

                fUser?.name?.set("属性变化fUser")//Smart cast to 'FoUser' is impossible 因为对象可null

                oUser?.age = 22
                oUser?.name = "改名啦"
            }

            adapter = BdAdapter()
            info = ItemBean(0, "include 的item")
            //bdtool中去查看
            imgRes = getDrawable(R.drawable.ic_launcher_background)

        }

    }
}

