package com.cainiaowo.netdemo.retrofit

import com.cainiaowo.netdemo.okhttp.config.CaiNiaoInterceptor
import com.cainiaowo.netdemo.okhttp.config.KtHttpLogInterceptor
import com.cainiaowo.netdemo.okhttp.config.LocalCookieJar
import com.cainiaowo.netdemo.retrofit.support.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 封装Retrofit
 */
object KtRetrofit {

    private val mOkHttpClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .followRedirects(false)
        .cookieJar(LocalCookieJar())
        .addNetworkInterceptor(CaiNiaoInterceptor())
        .addNetworkInterceptor(KtHttpLogInterceptor {
            logLevel(KtHttpLogInterceptor.LogLevel.BODY)
        })
        .build()

    private val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())//gson的支持
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .client(mOkHttpClient)

    /**
     * retrofit请求client
     */
    private var retrofit: Retrofit? = null

    /**
     * 初始化Retrofit配置
     * [baseUrl] 接口的基类url,以/结尾
     * okHttpClient: OkHttpClient = mOkHttpClient 默认的可传 可不传
     */
    fun initConfig(baseUrl: String, okHttpClient: OkHttpClient = mOkHttpClient): KtRetrofit {
        retrofit = retrofitBuilder.baseUrl(baseUrl).client(okHttpClient).build()
        return this
    }

    /**
     * 获取Retrofit的Service对象
     * [serviceClazz] 定义的Retrofit的Service接口类
     *
     * <T>	声明泛型参数 T，表示接口类型
     * Class<T>	传入接口类的类型信息（运行时通过反射使用）
     * return retrofit.create(serviceClazz)	动态生成接口的代理实现类，并返回 T 类型的实例
     *
     *interface ApiService { ... }
     * val apiService: ApiService = retrofit.getService(ApiService::class.java) //这个是对的 ✅
     * // 错误用法：Class 对象未指定泛型类型
     * val apiService = retrofit.getService(object : Class<Any>() {}) // ❌
     */
    fun <T> getService(serviceClazz: Class<T>): T {
        if (retrofit == null) {
            throw UninitializedPropertyAccessException("Retrofit必须初始化,需要配置baseUrl")
        } else {
            return this.retrofit!!.create(serviceClazz)
        }
    }
}