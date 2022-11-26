package com.caoniaowo.app

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class AppleFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("AppleFragment", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fg_apple, container, false)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("AppleFragment", "onPause")
    }


    override fun onPause() {
        super.onPause()
        Log.d("AppleFragment", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("AppleFragment", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("AppleFragment", "onDestroy")
    }
}