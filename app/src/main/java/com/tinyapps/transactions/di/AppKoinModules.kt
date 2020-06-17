package com.tinyapps.transactions.di

import com.tinyapps.data.di.getDataKoinModule
import com.tinyapps.domain.di.getDomainKoinModule
import com.tinyapps.presentation.di.getPresentationKoinModule
import org.koin.core.module.Module

fun getAppKoinModule(): List<Module> {

    val modules = mutableListOf<Module>()
    modules.addAll(getPresentationKoinModule())
    modules.addAll(getDomainKoinModule())
    modules.addAll(getDataKoinModule())
    return modules
}



