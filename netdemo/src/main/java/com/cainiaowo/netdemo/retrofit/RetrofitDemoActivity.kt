package com.cainiaowo.netdemo.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import com.blankj.utilcode.util.LogUtils
import com.cainiaowo.netdemo.R
import com.cainiaowo.netdemo.okhttp.model.NetResponse
import com.cainiaowo.netdemo.retrofit.model.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

class RetrofitDemoActivity : AppCompatActivity() {

    private val tvGetResult: TextView by lazy {
        findViewById(R.id.tv_get_result)
    }

    private val tvPostResult: TextView by lazy {
        findViewById(R.id.tv_post_result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_demo)
    }

    fun get(view: View) {
        testGet()
    }

    fun post(view: View) {
        testPost()
    }

    private fun testGet() {
        KtRetrofit.initConfig("https://course.api.cniao5.com/")
            .getService(CaiNiaoService::class.java)
            .userInfo()
            .observe(this) {
                LogUtils.e("liveData: $it")
                tvGetResult.text = it.toString()
            }
    }

    private fun testPost() {
        KtRetrofit.initConfig("https://course.api.cniao5.com/")
            .getService(CaiNiaoService::class.java)
            .login(LoginReq())
            .observe(this) {
                LogUtils.e("liveData: $it")
                tvPostResult.text = it.toString()
            }
    }
}

data class LoginReq(
    val mobi: String = "13067732886",
    val password: String = "123456789"
)

interface CaiNiaoService {
    @GET("member/userinfo")
    fun userInfo(): LiveData<ApiResponse<NetResponse>>

    @POST("accounts/course/10301/login")
    fun login(@Body body: LoginReq): LiveData<ApiResponse<NetResponse>>
}