package com.tinyapps.transactions.ui.state

import androidx.compose.Model

/**
 * Created by ChuTien on ${1/25/2017}.
 */
@Model
data class AmountState (var value : Float? = null,var max : Float = 10000f)