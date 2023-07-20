package com.pdxx.kotlinlearn.moduleFunny

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hym.netdemo.serverResult

import com.pdxx.kotlinlearn.net.onFailure
import com.pdxx.kotlinlearn.net.onSuccess

class JokeRepo(private val service: IFunnyService) : IJokeResource {

    private val _liveJokeList = MutableLiveData<JokeBean>()

    override val liveJokeList: LiveData<JokeBean>
        get() = _liveJokeList


    override suspend fun getJokeList(page: Int, size: Int) {
        TODO("Not yet implemented")
        service.getVideo(page,size)
            .serverResult()
            .onSuccess {


            }
            .onFailure {

            }

    }

}