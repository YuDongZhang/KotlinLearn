package com.pdxx.kotlinlearn.base

import android.app.Application
import com.pdxx.kotlinlearn.di.cnModules
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(cnModules)
        }

        //外部加载models
        loadKoinModules(otherMo)
    }

    initConfig()
}

val otherMo = module {

}

class otherModule(){

}