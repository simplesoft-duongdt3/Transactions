package com.tinyapps.domain.features.transactions.di

import com.tinyapps.domain.features.transactions.usecase.GetTransactionsUseCase
import org.koin.dsl.module

val transactionModule = module {

    single {
        GetTransactionsUseCase(
            transactionRepository = get()
        )
    }
}