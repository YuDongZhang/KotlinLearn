package com.pdxx.kotlinlearn.moduleFunny

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hym.netdemo.serverResult

import com.pdxx.kotlinlearn.net.onFailure
import com.pdxx.kotlinlearn.net.onSuccess

class JokeRepo(private val service: IJokeService) : IJokeResource {

    private val _liveJokeList = MutableLiveData<JokeBean>()

    override val liveJokeList: LiveData<JokeBean>
        get() = _liveJokeList


    override suspend fun getJokeList(page: Int, size: Int) {
        service.getVideo(page,size)
            .serverResult()
            .onSuccess {
                Log.i("TAG", "getJokeList: "+this@JokeRepo)

            }
            .onFailure {

            }

    }

}