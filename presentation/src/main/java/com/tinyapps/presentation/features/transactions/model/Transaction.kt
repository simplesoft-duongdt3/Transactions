package com.tinyapps.presentation.features.transactions.model

import kotlin.random.Random

val names = listOf("Vietcombank", "Agribank", "Sacombank", "Vietinbank")
val comments = listOf("Deposit", "Withdrawal", "Transfer")

data class Transaction(
    var id: String = "1",
    var name: String = names[Random.nextInt(names.size - 1)],
    var amount: Double = Random.nextDouble(-2000.0, 2000.0),
    var date: Long = 1593070307028,
    var comment: String = comments[Random.nextInt(comments.size - 1)],
    var tags: List<String> = arrayListOf(),
    var accountId: String = "1"
)