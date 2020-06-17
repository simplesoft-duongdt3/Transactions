package com.tinyapps.data.features.transactions.services

import com.tinyapps.data.features.transactions.models.TransactionListResponse

class TransactionApiServiceImpl : TransactionApiService {
    override suspend fun getTransactionList(): TransactionListResponse? {
        return null
    }
}