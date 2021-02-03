package com.mkapps.ecard.ui.orderdetail

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.github.ybq.android.spinkit.SpinKitView
import com.mkapps.ecard.R
import com.mkapps.ecard.managers.ServiceManager
import com.mkapps.ecard.model.OrderItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OrderDetailActivity : AppCompatActivity() {

    lateinit var orderIdTV : TextView
    lateinit var orderNameTv: TextView
    lateinit var orderLocationTv: TextView
    lateinit var orderAddressTv: TextView
    lateinit var orderStatusTv: TextView
    lateinit var orderPriceTv: TextView
    lateinit var orderTimeTv: TextView
    lateinit var orderNoteTv: TextView
    lateinit var webView: WebView
    lateinit var spinKitView : SpinKitView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = "Thông tin đơn hàng"
        spinKitView = findViewById(R.id.spin_kit)
        orderIdTV = findViewById(R.id.orderIdTv)
        orderNameTv = findViewById(R.id.qrNameTv)
        orderLocationTv = findViewById(R.id.locationNameTv)
        orderAddressTv = findViewById(R.id.locationAddTv)
        orderStatusTv = findViewById(R.id.orderStatusTv)
        orderPriceTv = findViewById(R.id.priceTv)
        orderTimeTv = findViewById(R.id.timeTv)
        orderNoteTv = findViewById(R.id.noteTv)
        webView = findViewById(R.id.webView)
        val orderId = intent.getStringExtra("orderId")
        orderId?.let {
            loadOrderDetail(it)
        }

    }
    fun updateUI(orderItem: OrderItem) {
        orderIdTV.text = orderItem.order_id
        orderNameTv.text = orderItem.qr_name
        orderAddressTv.text = orderItem.address
        orderLocationTv.text = orderItem.location_name
        orderStatusTv.text = orderItem.payment_status_mgs
        orderPriceTv.text = orderItem.price
        orderNoteTv.text = orderItem.note
        orderTimeTv.text = orderItem.date_created
        webView.webViewClient = WebViewClient()
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        orderItem.map_url?.let {
            webView.loadUrl(it)

        }
    }
    fun loadOrderDetail(orderId: String){
        showLoading(true)
        val pref = getSharedPreferences("ECard", Activity.MODE_PRIVATE)
        val token = pref?.getString("token", "")
        if (!TextUtils.isEmpty(token)){
            val webservice = ServiceManager.getInstance()
            webservice.getWebserviceApi().getHistoryDetail(token!!, orderId).enqueue(object :
                Callback<OrderItem> {
                override fun onResponse(
                    call: Call<OrderItem>,
                    response: Response<OrderItem>
                ) {
                    showLoading(false)

                    if (response.isSuccessful && response.body() != null) {
                        response.body()?.let {
                            updateUI(it)
                        }
                    } else {
                        MaterialDialog(this@OrderDetailActivity).show {
                            title(R.string.error)
                            message(text = response.message())
                            positiveButton(text = "OK", click = {

                            })

                            lifecycleOwner(this@OrderDetailActivity)

                        }
                    }

                }

                override fun onFailure(call: Call<OrderItem>, t: Throwable) {
                    t.printStackTrace()
                    showLoading(false)
                    MaterialDialog(this@OrderDetailActivity).show {
                        title(R.string.error)
                        message(text = t.localizedMessage)
                        positiveButton(text = "OK", click = {

                        })

                        lifecycleOwner(this@OrderDetailActivity)

                    }
                }
            })
        }
    }
    fun showLoading(showLoading: Boolean) {
        runOnUiThread {
            spinKitView.visibility = View.GONE
            if (showLoading) {
                spinKitView.visibility = View.VISIBLE
            }
        }

    }
}