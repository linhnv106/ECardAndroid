package com.mkapps.ecard.model

/**
 * Created by : Linh Nguyen  1/24/21
 */
data class DataToken(val token: String,
                    val id: Int,
                     val email: String?,
                     val nicename: String?,
                     val firstName: String?,
                     val lastName: String?,
                     val displayName: String?
)
data class LoginResponseModel(val success: Boolean,
                              val data: DataToken?,
                              val message: String?
)
data class LoginData(val username: String, val password: String)