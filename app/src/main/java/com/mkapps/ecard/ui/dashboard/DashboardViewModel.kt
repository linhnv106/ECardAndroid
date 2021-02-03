package com.mkapps.ecard.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mkapps.ecard.managers.ServiceManager
import com.mkapps.ecard.model.OrderItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
    var historyItem = MutableLiveData<ArrayList<OrderItem>>()
    fun loadHistory(token: String) {
        val webservice = ServiceManager.getInstance()
        webservice.getWebserviceApi().getHistory(token).enqueue(object : Callback<ArrayList<OrderItem>> {
            override fun onResponse(
                call: Call<ArrayList<OrderItem>>,
                response: Response<ArrayList<OrderItem>>
            ) {
                Log.i(DashboardViewModel::class.simpleName,"onRespone : $response")
                if (response.isSuccessful && response.body() != null){
                    response.body()?.let {
                        historyItem.value?.clear()
                        historyItem.postValue(it)

                    }
                }

            }

            override fun onFailure(call: Call<ArrayList<OrderItem>>, t: Throwable) {
                    t.printStackTrace()
            }
        })

    }
}