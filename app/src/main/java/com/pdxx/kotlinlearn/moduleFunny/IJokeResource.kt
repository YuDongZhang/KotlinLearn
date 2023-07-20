package com.pdxx.kotlinlearn.moduleFunny

import androidx.lifecycle.LiveData

interface IJokeResource {

    val liveJokeList: LiveData<JokeBean>

    suspend fun getJokeList(page: Int, size: Int)

}