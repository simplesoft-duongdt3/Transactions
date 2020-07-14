package com.tinyapps.presentation.features.transactions.di

import com.tinyapps.presentation.mapper.TagListMapper
import com.tinyapps.presentation.mapper.TransactionMapper
import com.tinyapps.presentation.features.transactions.viewmodel.TransactionViewModel
import com.tinyapps.presentation.mapper.AccountMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    factory { TagListMapper() }
    factory { TransactionMapper() }
    factory { AccountMapper() }
    viewModel {
        TransactionViewModel(
            transactionsUseCase = get(),
            appDispatchers = get(),
            createTransactionUseCase = get(),
            transactionListMapper = get(),
            accountsUseCase = get(),
            tagListMapper = get(),
            accountMapper = get()
        )
    }
}