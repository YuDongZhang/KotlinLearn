package com.pdxx.kotlinlearn.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import com.pdxx.kotlinlearn.R

class CompoundView(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    private val editText: EditText

    init {
        LayoutInflater.from(context).inflate(R.layout.view_compound, this, true)
        editText = findViewById(R.id.edit_text)
    }

    fun getText(): String {
        return editText.text.toString()
    }
}
