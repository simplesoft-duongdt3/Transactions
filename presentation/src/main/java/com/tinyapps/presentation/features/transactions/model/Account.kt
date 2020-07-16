package com.tinyapps.presentation.features.transactions.model

import kotlin.random.Random


data class Account(
    var id: String = "1",
    var name: String = names[Random.nextInt(names.size - 1)],
    var total: Double = 0.0
)