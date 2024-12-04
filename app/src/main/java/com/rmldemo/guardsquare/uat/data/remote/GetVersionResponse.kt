package com.rmldemo.guardsquare.uat.data.remote

import com.google.gson.annotations.SerializedName

data class GetVersionResponse(
    @SerializedName("data")
    val data: List<VersionResponse>
)
