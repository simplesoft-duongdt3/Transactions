package com.tinyapps.presentation.base.navigation

import androidx.lifecycle.MutableLiveData
import com.tinyapps.presentation.base.BaseViewModel

class NavigateViewModel : BaseViewModel() {
    val navigateLiveData = MutableLiveData<NavigateAction>()

    fun navigateBack() {
        navigateLiveData.value =
            NavigateAction.BackAction
    }

    fun navigateTo(toNavigation: NavigateAction.ToAction) {
        navigateLiveData.value = toNavigation
    }

}