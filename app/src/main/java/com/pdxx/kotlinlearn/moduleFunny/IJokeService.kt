package com.pdxx.kotlinlearn.moduleFunny

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IJokeService {
    /**
     * 使用协程
     */
    @GET("api/getHaoKanVideo")
    fun getVideo( @Query("page") page: Int, @Query("size") size: Int):Call<JokeBean>



//    @GET("accounts/phone/is/register")
//    fun checkRegister(@Query("mobi") mobi: String): Call<BaseDataRsp>

//    @POST("jswjw/login")
//    fun login(
//        @Query("userCode") userCode: String,
//        @Query("userPasswd") userPasswd: String,
//    ): Call<LoginRsp>
}