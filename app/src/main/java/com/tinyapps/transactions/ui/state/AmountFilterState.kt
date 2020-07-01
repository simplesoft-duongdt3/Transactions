package com.tinyapps.transactions.ui.state

import androidx.compose.getValue
import androidx.compose.mutableStateOf
import androidx.compose.setValue

/**
 * Created by ChuTien on ${1/25/2017}.
 */
class AmountFilterState(value: Double? = 1000.0, max: Double = 2000.0) {
    var value by mutableStateOf(value)
    var max by mutableStateOf(max)
}