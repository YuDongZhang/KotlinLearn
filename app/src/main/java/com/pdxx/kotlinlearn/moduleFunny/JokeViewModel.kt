package com.pdxx.kotlinlearn.moduleFunny

class JokeViewModel(private val resource: IJokeResource) : BaseViewModel() {

    val res = resource.liveJokeList

    internal fun repoList() {
        serveLaunch {
            resource.getJokeList(1, 10)
        }
    }

}