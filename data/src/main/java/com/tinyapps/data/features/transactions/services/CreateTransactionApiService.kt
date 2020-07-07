package com.tinyapps.data.features.transactions.services

import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CreateTransactionApiService {

    @FormUrlEncoded
    @POST("/forms/u/0/d/e/1FAIpQLSdXC3FdvBL7T2khA2U5J5vkl8g7CSWu1yQr7WKafOp7iszTvg/formResponse")
    suspend fun createTransaction(
        @Field("entry.2014286246") name: String,
        @Field("entry.1393991496") description: String,
        @Field("entry.1580347") amount: Double,
        @Field("entry.375385192_day") day: Int,
        @Field("entry.375385192_month") month: Int,
        @Field("entry.375385192_year") year: Int,
        @Field("entry.1360259595") tags: String
    ): ResponseBody
}