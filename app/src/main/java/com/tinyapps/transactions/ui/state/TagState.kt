package com.tinyapps.transactions.ui.state

import androidx.compose.frames.ModelList
import androidx.compose.getValue
import androidx.compose.mutableStateOf
import androidx.compose.setValue

/**
 * Created by ChuTien on ${1/25/2017}.
 */
class TagState (selectedOption : ModelList<String> = ModelList()){
    var selectedOption by mutableStateOf(selectedOption)

}