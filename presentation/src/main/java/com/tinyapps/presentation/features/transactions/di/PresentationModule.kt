package com.tinyapps.presentation.features.transactions.di

import com.tinyapps.data.features.transactions.mapper.TagListMapper
import com.tinyapps.data.features.transactions.mapper.TransactionMapper
import com.tinyapps.presentation.features.transactions.viewmodel.TransactionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    factory { TagListMapper() }
    factory { TransactionMapper() }
    viewModel {
        TransactionViewModel(
            transactionsUseCase = get(),
            appDispatchers = get(),
            createTransactionUseCase = get(),
            transactionListMapper = get(),
            tagListMapper = get()
        )
    }
}