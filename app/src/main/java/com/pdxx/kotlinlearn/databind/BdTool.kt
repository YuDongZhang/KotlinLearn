package com.pdxx.kotlinlearn.databind

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.pdxx.kotlinlearn.R

//这里BindMethods可以放在任何的类上面，重点在于内部属性声明
/*
 <androidx.appcompat.widget.AppCompatImageView
            image="@{imgRes}"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />
 */
// imgRes = getDrawable(R.drawable.ic_launcher_background) 直接给值就可以
// <variable
//       name="imgRes"
//       type="android.graphics.drawable.Drawable" />

@BindingMethods(
    BindingMethod(
        type = AppCompatImageView::class,//作用的控件类型
        attribute = "image", //这个是自定义的属性值
        method = "setImageDrawable" //作为 setImageDrawable
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
//这个和上面的区别是引入的方式不同
// <import type="com.cniao5.demo.BdTool" />
// <import type="com.cniao5.demo.BdToolKt" />
// 在使用的 时候是 上的 BdTool.getTitle 下面是 BdToolKt.getTitle 可以在界面查看
fun getTitle(type: String): String {
    return "type:$type"
}

//bingingAdapter不支持的一些属性进行转换 , android:textColor 对应你需要改变的 ,
//做的是一个转换
//top level 就是静态的，如果放在object内需要@jvmStatic
//界面中 android:textColor="@{info.type}" 值直接是数字,这个本来有问题的 , 所以 @BindingAdapter("android:textColor",
// requireAll = false) 要把这个参数引入 .
//getColorId(view: TextView, type: Int)前面第一个参数是我们对于的 view控件 ,后面type是要传的类型
//方法名字是可以随便取的 getColorId()
//这个地方不需要在哪里引用 , 直接界面用就可以 , 对应的就是 android:textColor
@BindingAdapter("android:textColor", requireAll = false)
fun getColorId(view: TextView, type: Int) {//前后参数要对应
    val color = when (type) {
        0 -> R.color.colorAccent
        1 -> R.color.colorPrimaryDark
        2 -> android.R.color.holo_red_dark
        3 -> android.R.color.holo_orange_dark
        else -> R.color.colorPrimary
    }
    //这一步做了颜色的设置
    view.setTextColor(view.context.resources.getColor(color))//这里的color就是上面的
}

//让属性字段支持databing , 在界面查看
//android:background="@{'red'} 比如你在代码写这个 , 肯定是不行的 , 这里做转换
//converStr2Color () 也是可以随便命名的
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