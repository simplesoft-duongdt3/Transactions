package com.tinyapps.data.features.transactions.models

data class TransactionListResponse(val transactions: List<Transaction?>) {
    data class Transaction(
        val id: Int,
        val name: String,
        val date: Long,
        val value: Double,
        val description: String,
        val tags: List<String>
    )
}