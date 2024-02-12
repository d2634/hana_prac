package com.example.dahee_prac

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface HanaAPI {

    /*@FormUrlEncoded -> 이거는 feild가 있을 때 사용?
    @POST("common/appInfo.do")
    fun getHanaData(
        @Header("Accept") Accept: String,
        @Header("User-Agent") UserAgent: String
    ): Call<ResultHanaAPI> */



    @POST("common/appInfo.do")
    fun getHanaData(
        @Header("Accept") Accept: String,
        @Header("User-Agent") UserAgent: String
    ): Call<ResultHanaAPI>

    @GET("posts/1")
    fun getUser(): Call<ResponseBody>
}