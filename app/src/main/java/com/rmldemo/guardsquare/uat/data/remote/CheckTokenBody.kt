package com.rmldemo.guardsquare.uat.data.remote

import com.google.gson.annotations.SerializedName

data class CheckTokenBody(
    @SerializedName("check_token")
    val checkToken: String
)
