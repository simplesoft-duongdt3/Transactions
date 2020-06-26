package com.tinyapps.data.features.transactions.di

import com.tinyapps.data.features.transactions.mapper.TransactionListMapper
import com.tinyapps.data.features.transactions.repository.TransactionRepositoryImpl
import com.tinyapps.data.features.transactions.services.TransactionApiService
import com.tinyapps.data.features.transactions.services.TransactionApiServiceImpl
import com.tinyapps.domain.features.transactions.repository.TransactionRepository
import org.koin.dsl.module

val dataModule = module {

    factory {
        TransactionListMapper()
    }

    single<TransactionApiService> {
        TransactionApiServiceImpl()
    }

    single<TransactionRepository> {
        TransactionRepositoryImpl(
            transactionApiService = get(),
            transactionListMapper = get()
        )
    }
}