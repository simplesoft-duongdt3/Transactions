package com.tinyapps.domain.features.transactions.models

data class TransactionListEntity(val transactions: List<Transaction>) {
    data class Transaction(
        val id: Int,
        val name: String,
        val description: String,
        val value: Double,
        val date: Long,
        val tags: List<String>
    )
}