package com.mkapps.ecard.model

/**
 * Created by : Linh Nguyen  1/25/21
 */
data class OrderItem (
    var id: String? = null,
    var title: String? = null,
    var price: String? = null,
    var date_created: String? = null,
    var qr_name: String? = null,
    var location_name: String? = null,
    var order_id: String? = null,
    var address: String? = null,
    var payment_status_mgs: String? = null,
    var payment_status: String? = null,
    var map_url: String? = null,
    var note: String? = null
)
