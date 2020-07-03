package com.tinyapps.transactions.ui.state

import androidx.compose.frames.ModelList
import androidx.compose.getValue
import androidx.compose.mutableStateOf
import androidx.compose.setValue
import com.tinyapps.presentation.features.transactions.model.Transaction
import com.tinyapps.presentation.features.transactions.model.comments
import com.tinyapps.presentation.features.transactions.model.names
import kotlin.random.Random

/**
 * Created by ChuTien on ${1/25/2017}.
 */
class TransactionInputState(
    name: String = names[Random.nextInt(names.size - 1)],
    amount: Double = Random.nextDouble(-2000.0, 2000.0),
    date: Long = 1593070307028,
    comment: String = comments[Random.nextInt(comments.size - 1)],
    tags: List<String> = arrayListOf()
) {
    var name by mutableStateOf(name)
    var amount by mutableStateOf(amount)
    var date by mutableStateOf(date)
    var comment by mutableStateOf(comment)
    var tags by mutableStateOf(tags)

}