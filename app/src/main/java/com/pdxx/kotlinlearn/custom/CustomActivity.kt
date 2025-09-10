package com.pdxx.kotlinlearn.custom

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.pdxx.kotlinlearn.R

class CustomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom)

        val btnInheritView: Button = findViewById(R.id.btn_inherit_view)
        val btnInheritExisting: Button = findViewById(R.id.btn_inherit_existing)
        val btnInheritViewGroup: Button = findViewById(R.id.btn_inherit_viewgroup)
        val btnCompoundView: Button = findViewById(R.id.btn_compound_view)

        btnInheritView.setOnClickListener {
            replaceFragment(InheritViewFragment())
        }

        btnInheritExisting.setOnClickListener {
            replaceFragment(InheritExistingViewFragment())
        }

        btnInheritViewGroup.setOnClickListener {
            replaceFragment(InheritViewGroupFragment())
        }

        btnCompoundView.setOnClickListener {
            replaceFragment(CompoundViewFragment())
        }

        // Load the default fragment
        if (savedInstanceState == null) {
            replaceFragment(InheritViewFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}