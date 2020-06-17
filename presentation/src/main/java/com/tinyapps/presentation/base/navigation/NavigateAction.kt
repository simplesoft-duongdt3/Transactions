package com.tinyapps.presentation.base.navigation

sealed class NavigateAction {
    object BackAction : NavigateAction()
    abstract class ToAction : NavigateAction()
}