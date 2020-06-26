package com.tinyapps.data.di

import com.tinyapps.data.features.transactions.di.dataModule
import org.koin.core.module.Module

fun getDataKoinModule(): List<Module> {
    return listOf(
        dataModule
    )
}
