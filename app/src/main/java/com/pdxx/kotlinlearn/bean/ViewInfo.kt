package com.pdxx.kotlinlearn.bean

import android.view.View
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.qualifier.named
//在普通类中要用这个注解就要使用 KoinComponent 接口
class ViewInfo(val view:View) :KoinComponent{
    val s :Student by inject<Student>(named("name"))
    val p :PersonEntity  = get()

}