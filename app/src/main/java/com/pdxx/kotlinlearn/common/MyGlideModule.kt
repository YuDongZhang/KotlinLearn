package com.pdxx.kotlinlearn.common

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import java.io.InputStream

/**
 * 自定义Glide模块，用于配置Glide的行为。
 * 我们在这里将Glide的网络层替换为OkHttp，以便添加自定义的HTTP头。
 */
@GlideModule
class MyGlideModule : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        // 创建一个OkHttpClient，并添加一个拦截器来修改请求头
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    // 添加Referer头，以绕过某些网站的防盗链机制
                    .addHeader("Referer", "https://pic.netbian.com/")
                    .build()
                chain.proceed(request)
            }
            .build()

        // 使用我们自定义的OkHttpClient来替换Glide默认的网络加载器
        //暂时注释掉
//        val factory = OkHttpUrlLoader.Factory(client)
//        glide.registry.replace(GlideUrl::class.java, InputStream::class.java, factory)
    }

    // isManifestParsingEnabled = false 可选，用于禁用清单解析，
    // 如果你完全通过代码管理Glide模块，可以将其设置为false以提高初始启动性能。
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}
