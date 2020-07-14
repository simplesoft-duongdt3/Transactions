package com.tinyapps.domain.features.transactions.di

import com.tinyapps.domain.features.transactions.usecase.CreateTransactionUseCase
import com.tinyapps.domain.features.transactions.usecase.GetAccountInfoUseCase
import com.tinyapps.domain.features.transactions.usecase.GetTransactionsUseCase
import org.koin.dsl.module

val domainModule = module {

    single {
        CreateTransactionUseCase(
            transactionRepository = get()
        )
    }

    single {
        GetTransactionsUseCase(
            transactionRepository = get()
        )
    }
    single {
        GetAccountInfoUseCase(
            transactionRepository = get()
        )
    }
}