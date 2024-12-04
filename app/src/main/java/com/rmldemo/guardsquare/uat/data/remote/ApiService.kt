package com.rmldemo.guardsquare.uat.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {
    @GET("promo")
    suspend fun getPromos(): List<PromoResponse>

    @GET
    suspend fun getVersion(
        @Url url: String = "https://threatcast.vnetcloud.com/gs/api/version?page=0&pageSize=10"
    ): GetVersionResponse

    @POST
    suspend fun postDeviceLog(
        @Url url: String = "https://threatcast.vnetcloud.com/gs/api/device-log",
        @Body deviceLogBody: DeviceLogBody
    ): PostDeviceLogResponse

    @POST
    suspend fun checkToken(
        @Url url: String = "https://threatcast.vnetcloud.com/gs/api/check-token",
        @Body checkTokenBody: CheckTokenBody
    ): CheckTokenResponse
}