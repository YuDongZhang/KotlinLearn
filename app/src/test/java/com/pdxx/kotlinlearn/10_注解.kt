package com.pdxx.kotlinlearn

import org.junit.Test

class `10_注解` {

    // Kotlin注解的声明
    //和一般的声明很类似，只是在class前面加上了annotation修饰符
    annotation class ApiDoc(val value: String)

    // 如何使用Kotlin注解？ 在Kotlin中使用注解和Java一样：
    @ApiDoc("修饰类")
    class Box {
        @ApiDoc("修饰字段")
        val size = 6

        @ApiDoc("修饰方法")
        fun test() {
        }
    }

    public enum class Method { GET, POST }

    @Target(AnnotationTarget.CLASS)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class HttpMethod(val method: Method)
    interface Api {
        val name: String
        val version: String
            get() = "1.0"
    }

    @HttpMethod(Method.GET)
    class ApiGetArticles : Api {
        override val name: String
            get() = "/api.articles"
    }


    fun fire(api: Api) {
        val annotations = api.javaClass.annotations
        val method = annotations.find { it is HttpMethod } as? HttpMethod
        println("通过注解得知该接口需要通过：${method?.method} 方式请求")
    }

    @Test
    fun test(){
        fire(ApiGetArticles())
    }

}