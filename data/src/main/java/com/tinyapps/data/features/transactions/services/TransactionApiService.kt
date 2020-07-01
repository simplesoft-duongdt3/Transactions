package com.tinyapps.data.features.transactions.services

import com.tinyapps.data.features.transactions.models.TransactionListResponse
import retrofit2.http.GET

interface TransactionApiService {
    @GET("/feeds/list/1UC5I0gqYsMBN7yBEbaNU8ny3sEekiernRCdgb_-SeBY/ogdajwa/public/values?alt=json")
    suspend fun getTransactionList() : TransactionListResponse?
}