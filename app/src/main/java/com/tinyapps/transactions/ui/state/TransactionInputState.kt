package com.tinyapps.transactions.ui.state

import androidx.compose.getValue
import androidx.compose.mutableStateOf
import androidx.compose.setValue

/**
 * Created by ChuTien on ${1/25/2017}.
 */
class TransactionInputState(
    name: String = "",
    amount: Float = 0f,
    date: Long = 0,
    comment: String = "",
    tags: List<String> = arrayListOf()
) {
    var name by mutableStateOf(name)
    var amount by mutableStateOf(amount)
    var date by mutableStateOf(date)
    var comment by mutableStateOf(comment)
    var tags by mutableStateOf(tags)

}