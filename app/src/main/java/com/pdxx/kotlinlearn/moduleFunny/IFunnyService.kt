package com.pdxx.kotlinlearn.moduleFunny

import androidx.lifecycle.LiveData
import retrofit2.http.GET
import retrofit2.http.Query

interface IFunnyService {
    /**
     * 使用协程
     */
    @GET("api/getHaoKanVideo")
    fun getVideo( @Query("userCode") page: Int, @Query("userPasswd") size: Int)
    :LiveData<JokeBean>



//    @GET("accounts/phone/is/register")
//    fun checkRegister(@Query("mobi") mobi: String): Call<BaseDataRsp>

//    @POST("jswjw/login")
//    fun login(
//        @Query("userCode") userCode: String,
//        @Query("userPasswd") userPasswd: String,
//    ): Call<LoginRsp>
}