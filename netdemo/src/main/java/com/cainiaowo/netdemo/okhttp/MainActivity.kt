package com.cainiaowo.netdemo.okhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.cainiaowo.netdemo.R
import com.cainiaowo.netdemo.okhttp.model.NetResponse
import com.cainiaowo.netdemo.okhttp.support.CaiNiaoUtils
import com.cainiaowo.netdemo.okhttp.support.IHttpCallback
import com.cainiaowo.netdemo.retrofit.KtRetrofit
import com.cainiaowo.netdemo.retrofit.model.ApiEmptyResponse
import com.cainiaowo.netdemo.retrofit.model.ApiErrorResponse
import com.cainiaowo.netdemo.retrofit.model.ApiResponse
import com.cainiaowo.netdemo.retrofit.model.ApiSuccessResponse
import com.cainiaowo.netdemo.retrofit.support.serverResponse
import com.hym.netdemo.serverRsp
import com.hym.netdemo.toLivedata
import kotlinx.coroutines.launch
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getResult: TextView = findViewById(R.id.tv_get_result)
        val postResult: TextView = findViewById(R.id.tv_post_result)


        //region retrofit 请求 , 看最下面的返回 , 返回的是 call
//        val retrofitCall = KtRetrofit.initConfig("https://course.api.cniao5.com/")
//            .getService(CniaoService::class.java)
//            .userInfo()

        lifecycleScope.launch{
            try {
                val response = KtRetrofit.initConfig("https://api.istero.com/")
                    .getService(CniaoService::class.java)
                    .getHaoKan("c047d98062f0bdaff2df9d4d7b617064")
                    .serverResponse()

                when (response) {
                    is ApiSuccessResponse -> {
                        val questions = response.body.data
                        getResult.text = "共加载 ${questions.size} 条热榜问题"

                        // 如果需要显示前几条数据可以这样做：
                        if (questions.isNotEmpty()) {
                            val firstQuestion = questions[0]
                            postResult.text = "最新问题：${firstQuestion.title}"
                        }
                    }
                    is ApiErrorResponse -> {
                        getResult.text = "加载失败：${response.errorMessage}"
                    }
                }

            }catch (e: Exception) {
                getResult.text = "发生异常：${e.message}"
            }



        }





        //登录这块用的也是post请求
//        val loginCall = KtRetrofit.initConfig(
//            "https://course.api.cniao5.com/",
//            OkHttpApi.getInstance().getClient()
//        )
//            .getService(CniaoService::class.java)
//            .login(LoginReq())

        /*
        loginCall.serverRsp() 挂起函数 , 所以要在协程中使用
         */
        //lifecycleScope 他可以启动协程 , 协程里面启动异步的函数
//        lifecycleScope.launch {
//            //表达式声明，使用when,协程，同步的代码形式，执行异步的操作
//            when (val serverRsp = loginCall.serverRsp()) {//serverRsp  用到的就是扩展函数  , 这块执行了网络请求得到网络请求的结果
//               //下面就是成功失败的打印
//                is ApiSuccessResponse -> {
//                    LogUtils.i("mika apiservice ${serverRsp.body.toString()}")
//                    // tvHello1.text = HymUtils.decodeData(serverRsp.body.data.toString())
//                }
//                is ApiErrorResponse -> {
//                    LogUtils.e("mika apiservice ${serverRsp.errorMessage}")
//                   // tvHello1.text = serverRsp.errorMessage
//                }
//                is ApiEmptyResponse -> {
//                    LogUtils.d("mika empty apireoponese")
//                   // tvHello1.text = "empty apireoponese"
//                }
//            }
//        }



//        KtRetrofit.initConfig("https://course.api.cniao5.com/")
//            .getService(CniaoService::class.java)
//            .userInfo2().observe(this, Observer {
//                LogUtils.d("mika retrofit liveRsp ${it.toString()}")
//            })
        //endregion

    }


    data class LoginReq(
        val mobi: String = "18648957777",
        val password: String = "cn5123456"
    )

    data class Zhihu1Req(
        val data:String = "rank"
    )
}


interface CniaoService {
    @POST("accounts/course/10301/login")
    fun login(@Body body: MainActivity.LoginReq): retrofit2.Call<NetResponse>

    @POST("accounts/course/10301/login")
    fun zhihu1(@Body body: MainActivity.Zhihu1Req): retrofit2.Call<NetResponse>


    @GET("member/userinfo")
    fun userInfo(): retrofit2.Call<NetResponse>

    @GET("resource/zhihu/top")
    fun getHaoKan(@Header("Authorization") authToken: String): retrofit2.Call<QuestionResponse>

    @GET("member/userinfo")
    fun userInfo2(): LiveData<ApiResponse<NetResponse>>
}

// 单条问题数据类
data class Question(
    val rank: Int,
    val title: String,
    val hot: String, // 保留原始"XX万"格式
    val url: String
)

// 整体响应数据类
data class QuestionResponse(
    val code: Int,
    val data: List<Question>,
    val message: String
)

data class LoginReq(val mobi: String = "13067732886", val password: String = "123456789")
// data class LoginReq(val mobi: String = "13067732886", val password: String = "66666666")