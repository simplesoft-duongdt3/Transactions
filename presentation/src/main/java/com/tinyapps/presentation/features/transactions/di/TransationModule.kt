package com.tinyapps.presentation.features.transactions.di

import com.tinyapps.presentation.features.transactions.viewmodel.TransactionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val transactionModule = module {

    viewModel {
        TransactionViewModel(
            transactionsUseCase = get(),
            appDispatchers = get()
        )
    }
}