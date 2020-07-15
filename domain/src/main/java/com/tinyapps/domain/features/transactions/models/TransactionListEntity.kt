package com.tinyapps.domain.features.transactions.models

data class TransactionListEntity(val transactions: List<Transaction>,val account: Account) {
    data class Transaction(
        val id: String,
        val name: String,
        val description: String,
        val value: Double,
        val date: Long,
        val tags: List<String>
    )

    data class Account(
        var name: String,
        var total: Double
    )
}