package com.tinyapps.transactions.ui.state

import androidx.compose.*
import com.tinyapps.transactions.ui.BackPressHandler

/**
 * Created by ChuTien on ${1/25/2017}.
 */
class AppState(openDialog : Boolean = false,numOfTransaction : Int = 0, private val backPressHandler: BackPressHandler){
    var openDialog by mutableStateOf(openDialog)
    var numOfTransaction by mutableStateOf(numOfTransaction)

    init {
        backPressHandler.setHandleBackPressEvent(object : BackPressHandler.HandleBackPressEvent {
            override fun onBackPress() {
                updateOpenDialogFlag(false)
            }
        })
    }

    fun updateOpenDialogFlag(openDialog: Boolean) {
        this.openDialog = openDialog
        backPressHandler.setCanBack(!this.openDialog)
    }
}