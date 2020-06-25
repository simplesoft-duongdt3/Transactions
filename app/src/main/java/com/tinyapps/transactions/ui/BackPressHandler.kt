package com.tinyapps.transactions.ui

class BackPressHandler(private var canBack: Boolean){
    private var handleBackPressEvent: HandleBackPressEvent? = null
    fun setCanBack(canBack: Boolean){
        this.canBack = canBack
    }
    fun onBackPress(): Boolean {
        if (canBack) {
            return true
        }
        handleBackPressEvent()
        return false
    }

    private fun handleBackPressEvent() {
        this.handleBackPressEvent?.onBackPress()
    }

    fun setHandleBackPressEvent(handleBackPressEvent: HandleBackPressEvent?) {
        this.handleBackPressEvent = handleBackPressEvent
    }

    interface HandleBackPressEvent {
        fun onBackPress()
    }
}