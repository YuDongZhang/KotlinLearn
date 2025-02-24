package com.cainiaowo.netdemo.okhttp.config

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * 持久化的CookieJar实现类
 *
 * Cookie管理类，需要确认Cookie的存储和过期处理是否正确。
 */
internal class LocalCookieJar : CookieJar {

    /**
     * cookie本地化存储
     */
    private val cache = mutableListOf<Cookie>()

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        // 过期的cookie
        val invalidCookies: MutableList<Cookie> = ArrayList()
        // 有效的cookie
        val validCookies: MutableList<Cookie> = ArrayList()

        for (cookie in cache) { //判断cookie的时候和当前时间对比
            if (cookie.expiresAt < System.currentTimeMillis()) {
                // 判断是否过期
                invalidCookies.add(cookie)
            } else if (cookie.matches(url)) {
                // 匹配cookie对应url
                validCookies.add(cookie)
            }
        }

        // 缓存中移除过期cookie
        cache.removeAll(invalidCookies)

        // 返回List<Cookie>让Request设置
        return validCookies

    }

    /**
     * 将cookie保存
     */
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cache.addAll(cookies)
    }
}