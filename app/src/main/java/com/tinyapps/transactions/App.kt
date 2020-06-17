package com.tinyapps.transactions

import androidx.multidex.MultiDexApplication
import com.tinyapps.transactions.di.getAppKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        initDi()
    }

    private fun initDi() {
        startKoin {
            androidContext(this@App)
            modules(
                getAppKoinModule()
            )
        }
    }
}