package com.pdxx.kotlinlearn.activity

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.databinding.ActivityNavigationBinding

/**
 * NavigationActivity: 导航的宿主 (Host)
 * 这个Activity承载了NavHostFragment，并作为所有Fragment导航的容器。
 */
class NavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        // Step 7: 获取NavController
        // NavController是Navigation组件的核心，它负责管理App的导航状态，并在NavHost中执行Fragment的切换操作。
        // 我们通过findNavController并传入NavHostFragment的ID来获取它的实例。
        val navController = findNavController(R.id.nav_host_fragment_activity_navigation)

        // Step 8: 配置AppBar
        // AppBarConfiguration用于配置Toolbar/ActionBar的行为。
        // 我们将顶层的几个Destination（Home, Dashboard, Notifications）的ID传给它。
        // 当用户在这些顶层页面时，ActionBar左侧的返回箭头（Up button）会自动隐藏。
        // 当用户导航到非顶层页面（如DetailsFragment）时，返回箭头会自动显示。
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )

        // Step 9: 将NavController与ActionBar关联
        // setupActionBarWithNavController会将NavController与Activity的ActionBar（或Toolbar）绑定。
        // 这样，当导航到不同Fragment时，ActionBar的标题会自动更新为该Fragment在Graph中定义的label。
        // 同时，它也会自动处理返回箭头的显示/隐藏和点击事件。
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Step 10: 将NavController与BottomNavigationView关联
        // setupWithNavController会将NavController与BottomNavigationView绑定。
        // 这使得点击底部导航栏的Item时，能够自动导航到对应的Destination，实现了无缝集成。
        navView.setupWithNavController(navController)
    }

    /**
     * Step 11: 处理返回按钮事件
     * 我们需要重写onSupportNavigateUp()方法，并将返回事件委托给NavController。
     * 这样，当用户点击ActionBar上的返回箭头时，NavController会负责处理Fragment的出栈操作。
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_navigation)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
