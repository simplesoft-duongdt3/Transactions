package com.tinyapps.presentation.features.transactions.model

import kotlin.random.Random

val names = listOf("Vietcombank", "Agribank", "Sacombank", "Vietinbank")
val comments = listOf("Deposit", "Withdrawal", "Transfer")

data class Transaction(
    val id: String="1",
    val name: String = names[Random.nextInt(names.size - 1)],
    val amount: Double = Random.nextDouble(-2000.0, 2000.0),
    val date: Long = 1593070307028,
    val comment: String = comments[Random.nextInt(comments.size - 1)],
    val tags: List<String> = arrayListOf()
)