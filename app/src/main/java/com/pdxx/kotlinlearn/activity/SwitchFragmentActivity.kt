package com.pdxx.kotlinlearn.activity


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.frag.Fragment01
import com.pdxx.kotlinlearn.frag.Fragment02
import com.pdxx.kotlinlearn.frag.ItemFragment

class SwitchFragmentActivity : AppCompatActivity() {

    private val fragments: Array<Fragment> by lazy {
        arrayOf(
            ItemFragment(),
            Fragment01(),
            Fragment02()
        )
    }

    private var currentFragment: Fragment = fragments[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch_fragment)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set the default fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frame_layout, currentFragment)
                .commit()
        }

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.navigation_home -> fragments[0]
                R.id.navigation_dashboard -> fragments[1]
                R.id.navigation_notifications -> fragments[2]
                else -> null
            }

            if (fragment != null && fragment != currentFragment) {
                switchFragment(fragment)
            }

            true
        }
    }

    private fun switchFragment(to: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        if (!to.isAdded) {
            transaction.hide(currentFragment).add(R.id.frame_layout, to)
        } else {
            transaction.hide(currentFragment).show(to)
        }
        currentFragment = to
        transaction.commit()
    }
}