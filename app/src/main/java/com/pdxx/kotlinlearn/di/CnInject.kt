package com.pdxx.kotlinlearn.di

import android.view.View
import com.pdxx.kotlinlearn.activity.ViewInfo
import com.pdxx.kotlinlearn.activity.ui.dashboard.DashboardViewModel
import com.pdxx.kotlinlearn.bean.PersonEntity
import com.pdxx.kotlinlearn.bean.Student
import com.pdxx.kotlinlearn.frag.Fragment01
import com.pdxx.kotlinlearn.vm.VmOne
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.TypeQualifier
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module


val cnModules = module {
    //此界面的效果可以从 mainactivity 去查看
    //空声明会报错
    //单例模式//为false用的时候创建
    single(createdAtStart = false) { PersonEntity() } bind Fragment01::class
    //bind的意思就是可以通过  get<Fragment01>()
    //工厂模式就是创建过程中眼不见为 干净

    //覆盖声明  //这个注释掉 ,上面打印的就是 一样的对象 , 地址一样  //这一段注销测试上面带
    factory(override = true) { PersonEntity() }

    //多构造函数
    factory(named("name")) { Student("hh") }

    //用type 类型进行标记                          //用get就是在此之前有声明的 //就是在上面声明的
    factory(TypeQualifier(PersonEntity::class)) { Student(get<PersonEntity>()) }

    //接受外部参数的形式
    factory { (view:View) -> ViewInfo(view) }

    //viewmodel创建
    viewModel { DashboardViewModel() }
    viewModel { VmOne() }


}
