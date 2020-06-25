package com.tinyapps.transactions.ui.state

import androidx.compose.getValue
import androidx.compose.mutableStateOf
import androidx.compose.setValue

/**
 * Created by ChuTien on ${1/25/2017}.
 */
class TagFilterState(selectedOption: String = "") {
    var selectedOption by mutableStateOf(selectedOption)
}