package com.pdxx.kotlinlearn.base

import android.app.Application
import com.pdxx.kotlinlearn.di.cnModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

abstract class BaseApplication : Application() {


    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidLogger(Level.ERROR)

            // 把application传给Koin依赖框架,可以在其它地方使用
            androidContext(this@BaseApplication)

//            modules(cnModules)
        }

        //外部加载models
        loadKoinModules(otherMo)

        initConfig()
    }

    /**
     * 初始化配置
     */
    protected open fun initConfig() {}

}



val otherMo = module {

}

class otherModule(){

}

//老版本的配置

/*
//    override fun onCreate() {
//        super.onCreate()
//        //Koin注解使用
//        startKoin {
//            androidLogger(Level.ERROR) //目前已知bug，除了level.error外，使用 androidloger会导致崩溃
//            //context
//            androidContext(this@MyApplication)
//            //assets 资源数据
//            androidFileProperties("ass.file")//默认取值assets下 properties文件内的属性配置，可自行配置
//
//            //加载需要的 module
//            modules(cnModules)
//        }
//    }

 */