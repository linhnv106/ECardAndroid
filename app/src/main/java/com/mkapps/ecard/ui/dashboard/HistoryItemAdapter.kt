package com.mkapps.ecard.ui.dashboard

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mkapps.ecard.R
import com.mkapps.ecard.model.OrderItem
import com.mkapps.ecard.ui.orderdetail.OrderDetailActivity

/**
 * Created by : Linh Nguyen  2/2/21
 */


class HistoryItemAdapter(private val dataSet: ArrayList<OrderItem>?, val context : Activity)  : RecyclerView.Adapter<OrderItemViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_rv_item, parent, false)

        return OrderItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        dataSet?.get(position)?.let {
            holder.bindView(it,context)
        }
    }

    override fun getItemCount(): Int {
        return dataSet?.size ?: 0
    }


}

class OrderItemViewHolder(val view : View): RecyclerView.ViewHolder(view){

    private val statusTV : TextView
    private val orderIdTv : TextView
    private val locationTv : TextView
    private val orderAddressTv: TextView
    private val orderTimeTv: TextView
    init {
        statusTV = view.findViewById(R.id.orderStatusTv)
        orderIdTv = view.findViewById(R.id.orderIdTv)
        locationTv = view.findViewById(R.id.locationTv)
        orderAddressTv = view.findViewById(R.id.orderAddressTv)
        orderTimeTv = view.findViewById(R.id.orderTimeTv)

    }
    fun bindView(orderId: OrderItem,context: Context) {
        statusTV.text = orderId.payment_status_mgs
        if (orderId.payment_status.equals( "-1",true) ){
            statusTV.setBackgroundColor(Color.DKGRAY)
        } else if (orderId.payment_status.equals( "1",true) ){
            statusTV.setBackgroundColor(Color.GREEN)
        } else if (orderId.payment_status.equals( "0",true) ) {
            statusTV.setBackgroundColor(Color.RED)
        }
        orderIdTv.text = "${orderId.order_id}"
        orderAddressTv.text = ""+orderId.address
        locationTv.text = "" + orderId.location_name
        orderTimeTv.text = ""+ orderId.date_created
        view.setOnClickListener {
            val intent = Intent(context,OrderDetailActivity::class.java)
            intent.putExtra("orderId",orderId.id)
            context.startActivity(intent)
        }
    }

}