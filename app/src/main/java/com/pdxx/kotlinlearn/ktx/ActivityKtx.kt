package com.pdxx.kotlinlearn.ktx

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.snackbar.Snackbar

/**
 * Activity扩展
 */
// region 扩展函数
/**
 * Activity中使用DataBinding时setContentView的简化
 * [layoutId] 布局文件资源id
 * @return 返回Binding对象实例
 *
 * 看原型: val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
 */
fun <T : ViewDataBinding> Activity.bindView(@LayoutRes layoutId: Int): T {
    return DataBindingUtil.setContentView<T>(this, layoutId)
}

/**
 * Activity中使用DataBinding时bind的简化
 * [root] The root View of the inflated binding layout.
 * @return 返回Binding对象实例,可null
 *
 * 上面方法重写
 * 原型 : if (view == null) {
view = View.inflate(getActivity(), R.layout.fragment_service_detail, null);
}
binding = DataBindingUtil.bind(view);
 *
 */
fun <T : ViewDataBinding> Activity.bindView(root: View): T? {
    return DataBindingUtil.bind<T>(root)
}

/**
 * 界面Activity的沉浸式状态栏,使得可以在状态栏里面显示部分需要的图片
 * 注意点:需要在setContentView之前调用该函数才生效
 */
fun Activity.immediateStatusBar() {
    window.apply {
        addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}

/**
 * 软键盘的隐藏
 * [view] 事件控件View
 */
fun Activity.dismissKeyBoard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}


/**
 * Set content view for activity, and return the binding.
 */
fun <T : ViewDataBinding> AppCompatActivity.bindContentView(layoutId: Int): T {
    return DataBindingUtil.setContentView(this, layoutId)
}

/**
 * Start an activity.
 */
inline fun <reified T : Activity> Context.startActivity(bundle: Bundle? = null) {
    val intent = Intent(this, T::class.java)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivity(intent)
}

/**
 * Show a toast.
 */
fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

/**
 * Show a snackbar.
 */
fun View.snackbar(message: CharSequence, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

// endregion

// region 扩展属性
/**
 * 扩展lifeCycleOwner字段,便于和Fragment之间使用lifeCycleOwner保持一致性
 */
val ComponentActivity.viewLifecycleOwner: LifecycleOwner
    get() = this

/**
 * Activity的扩展字段,便于和Fragment中使用liveData之类的时候保持一致性
 */
val Activity.context: Context
    get() = this
// endregion