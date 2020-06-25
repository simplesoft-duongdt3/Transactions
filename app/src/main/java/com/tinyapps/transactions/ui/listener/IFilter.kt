package com.tinyapps.transactions.ui.listener

import com.tinyapps.transactions.ui.state.AmountState
import com.tinyapps.transactions.ui.state.TagFilterState
import com.tinyapps.transactions.ui.state.TypeFilterState

/**
 * Created by ChuTien on ${1/25/2017}.
 */
interface IFilter {
    fun fillerResults(amountState: AmountState, tagState: TagFilterState, typeState: TypeFilterState)
    fun fillerCancel()
}