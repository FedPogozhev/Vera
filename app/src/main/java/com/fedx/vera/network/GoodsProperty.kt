package com.fedx.vera.network

import android.accounts.AuthenticatorDescription
import com.squareup.moshi.Json

data class GoodsProperty(
    val id: String, val groups: String,
    val fullName: String, val available: String, val unit: String,
    val urlImage: String, val price: String, val description: String
){
    val _available
    get() = available == "в наличии"
}