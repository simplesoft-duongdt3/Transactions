package com.tinyapps.domain.di

import com.tinyapps.domain.features.transactions.di.domainModule
import org.koin.core.module.Module

fun getDomainKoinModule(): List<Module> {
    return listOf(
        domainModule
    )
}
