package com.cainiaowo.netdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.TextView
import com.blankj.utilcode.util.LogUtils
import com.cainiaowo.netdemo.support.IHttpCallback

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val getResult: TextView = findViewById(R.id.tv_get_result)
        val postResult: TextView = findViewById(R.id.tv_post_result)
        val map = mapOf("key" to "free", "appid" to "0", "msg" to "你好")
        val httpApi: IHttpApi = OkHttpApi()
        // get请求
        httpApi.get(map, "api.php", object : IHttpCallback {
            override fun onSuccess(data: Any?) {
                LogUtils.d("success result : ${data.toString()}")
                runOnUiThread {
                    getResult.text = data.toString()
                }
            }

            override fun onFailed(error: Any?) {
                LogUtils.d("failed msg : ${error.toString()}")
            }
        })
        // post请求
        val loginBody = LoginReq()
        httpApi.post(loginBody, "", object : IHttpCallback {
            override fun onSuccess(data: Any?) {
                LogUtils.d("success result : ${data.toString()}")
                runOnUiThread {
                    postResult.text = data.toString()
                }
            }

            override fun onFailed(error: Any?) {
                LogUtils.d("failed msg : ${error.toString()}")
            }
        })
        // 取消post请求
        SystemClock.sleep(200)
        httpApi.cancelRequest(loginBody)
    }

    data class LoginReq(val mobi: String = "13067732886", val password: String = "66666666")
}