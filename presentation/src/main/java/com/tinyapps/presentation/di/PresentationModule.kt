package com.tinyapps.presentation.di

import com.tinyapps.presentation.base.AppDispatchers
import com.tinyapps.presentation.base.navigation.NavigateViewModel
import com.tinyapps.presentation.features.transactions.di.presentationModule
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun getPresentationKoinModule(): List<Module> {
    return listOf(
        presentationCommonModule,
        presentationModule
    )
}


private val presentationCommonModule = module {
    single { AppDispatchers(Dispatchers.Main, Dispatchers.IO) }

    viewModel { NavigateViewModel() }
}