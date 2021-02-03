package com.mkapps.ecard.service

import com.mkapps.ecard.model.LoginData
import com.mkapps.ecard.model.LoginResponseModel
import com.mkapps.ecard.model.OrderItem
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by : Linh Nguyen  1/24/21
 */
interface Webservice {
    @POST("jwt-auth/v1/token")
    fun getUser(@Body user: LoginData): Call<LoginResponseModel>

    @GET("v2/my-history")
    fun getHistory(@Header("Authorization") authorization: String): Call<ArrayList<OrderItem>>
    @GET("v2/my-history/{orderId}")
    fun getHistoryDetail(@Header("Authorization") authorization: String, @Path("orderId")  orderId: String): Call<OrderItem>
}