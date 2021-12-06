package com.pdxx.kotlinlearn.`5ClassAndObject`

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

class `15_协程` {

    @Test
    fun test1(){
        GlobalScope.launch {

            println("Hello,World!")

        }
    }
}