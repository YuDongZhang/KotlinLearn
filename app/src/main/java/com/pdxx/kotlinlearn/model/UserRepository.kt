package com.pdxx.kotlinlearn.model

import kotlinx.coroutines.delay

/**
 * MVVM中的 Model层 - 数据仓库
 * Repository负责提供数据。它将ViewModel与具体的数据来源（网络API、本地数据库等）隔离开来。
 * ViewModel不需要关心数据是从哪里来的，只需要向Repository请求即可。
 */
class UserRepository {

    /**
     * 模拟从网络或数据库获取用户数据。
     * 这是一个挂起函数 (suspend)，因为它模拟了一个耗时操作。
     *
     * @param userId 要获取的用户的ID
     * @return 返回一个UserData对象
     */
    suspend fun getUser(userId: String): UserData {
        // 模拟2秒的网络延迟
        delay(2000)

        // 模拟返回一个固定的用户数据
        return UserData(name = "张三", age = 30)
    }
}
