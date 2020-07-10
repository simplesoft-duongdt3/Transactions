package com.tinyapps.transactions.ui.state

import androidx.compose.*
import com.tinyapps.transactions.ui.BackPressHandler

/**
 * Created by ChuTien on ${1/25/2017}.
 */
class AppState(isLoading : Boolean = false,isShowDialog : Boolean = false, isShowTransactionInput : Boolean = false, numOfTransaction : Int = 0, private val backPressHandler: BackPressHandler){
    var isLoading by mutableStateOf(isLoading)
    var isShowDialog by mutableStateOf(isShowDialog)
    var isShowTransactionInput by mutableStateOf(isShowTransactionInput)
    var numOfTransaction by mutableStateOf(numOfTransaction)

    init {
        backPressHandler.setHandleBackPressEvent(object : BackPressHandler.HandleBackPressEvent {
            override fun onBackPress() {
                if(isShowDialog) {
                    updateOpenDialogFlag(false)
                }
                if(isShowTransactionInput) {
                    updateTransactionInputFlag(false)
                }
            }
        })
    }

    fun updateOpenDialogFlag(openDialog: Boolean) {
        this.isShowDialog = openDialog
        backPressHandler.setCanBack(!this.isShowDialog && !this.isShowTransactionInput)
    }
    fun updateTransactionInputFlag(isShowTransactionInput: Boolean) {
        this.isShowTransactionInput = isShowTransactionInput
        backPressHandler.setCanBack(!this.isShowDialog && !this.isShowTransactionInput)
    }
}