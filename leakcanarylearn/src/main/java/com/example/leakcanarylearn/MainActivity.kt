package com.example.leakcanarylearn

import android.os.Bundle
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val leakThread = LeakThread()
        leakThread.start()
    }

    inner class LeakThread : Thread() {
        override fun run() {
            super.run()
            try {
                sleep(6 * 60 * 1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

}

