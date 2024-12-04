package com.rmldemo.guardsquare.uat.data.remote

import com.google.gson.annotations.SerializedName

data class PromoResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("url")
    val url: String
)
