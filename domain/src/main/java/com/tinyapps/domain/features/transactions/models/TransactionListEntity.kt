package com.tinyapps.domain.features.transactions.models

data class TransactionListEntity(val transactions: List<Transaction>) {
    data class Transaction(val id: Int)
}