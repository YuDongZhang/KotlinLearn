package com.cniao5.demo

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods

//这里BindMethods可以放在任何的类上面，重点在于内部属性声明
@BindingMethods(
    BindingMethod(
        type = AppCompatImageView::class,
        attribute = "image",
        method = "setImageDrawable"
    )
)

//单列类
object BdTool {

    //申明静态的
    @JvmStatic
    fun getTitle(type: String): String {
        return "type:$type"
    }

}

//直接静态函数，top level的写法
fun getTitle(type: String): String {
    return "type:$type"
}

//bingingAdapter不支持的一些属性进行转换 , android:textColor 对应你需要改变的 ,
//做的是一个转换
//top level 就是静态的，如果放在object内需要@jvmStatic
@BindingAdapter("android:textColor", requireAll = false)
fun getColorId(view: TextView, type: Int) {//前后参数要对应
    val color = when (type) {
        0 -> R.color.colorAccent
        1 -> R.color.colorPrimaryDark
        2 -> android.R.color.holo_red_dark
        3 -> android.R.color.holo_orange_dark
        else -> R.color.colorPrimary
    }
    view.setTextColor(view.context.resources.getColor(color))//这里的color就是上面的
}

//让属性字段支持databing , 在界面查看
@BindingConversion
fun converStr2Color(str: String): Drawable {
    return when (str) {
        "red" -> {
            ColorDrawable(Color.RED)
        }
        "blue" -> ColorDrawable(Color.BLUE)
        else -> {
            ColorDrawable(Color.GRAY)
        }
    }
}