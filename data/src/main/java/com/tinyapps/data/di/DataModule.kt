package com.tinyapps.data.di

import com.tinyapps.data.features.transactions.di.transactionModule
import org.koin.core.module.Module

fun getDataKoinModule(): List<Module> {
    return listOf(
        transactionModule
    )
}
