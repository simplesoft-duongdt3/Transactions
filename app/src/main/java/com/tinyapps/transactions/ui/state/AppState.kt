package com.tinyapps.transactions.ui.state

import androidx.compose.Model
import androidx.compose.MutableState

/**
 * Created by ChuTien on ${1/25/2017}.
 */
@Model
data class AppState(var openDialog : Boolean = false)