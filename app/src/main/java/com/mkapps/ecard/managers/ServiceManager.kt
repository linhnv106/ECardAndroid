package com.mkapps.ecard.managers

import com.mkapps.ecard.service.Webservice
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by : Linh Nguyen  1/24/21
 */
class ServiceManager {
    companion object {
        val baseUrl = "https://ecard.darkvn.net/wp-json/"
        fun getInstance() : ServiceManager {
            return ServiceManager()
        }
    }
    val webservice : Webservice
        get() = getWebserviceApi()
    fun getWebserviceApi(): Webservice {
        val builder = OkHttpClient.Builder()
        val okHttpClient = builder.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(Webservice::class.java)
    }
}