package com.tinyapps.data.features.transactions.services

import com.tinyapps.data.features.transactions.models.TransactionListResponse

interface TransactionApiService {
    suspend fun getTransactionList() : TransactionListResponse?
}