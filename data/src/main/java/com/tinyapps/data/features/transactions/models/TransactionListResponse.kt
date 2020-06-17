package com.tinyapps.data.features.transactions.models

data class TransactionListResponse(val transactions: List<Transaction?>) {
    data class Transaction(val id: Int)
}