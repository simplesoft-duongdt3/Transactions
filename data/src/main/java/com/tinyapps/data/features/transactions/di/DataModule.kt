package com.tinyapps.data.features.transactions.di

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.tinyapps.data.features.transactions.exception_interceptor.RemoteExceptionInterceptor
import com.tinyapps.data.features.transactions.mapper.AccountInfoMapper
import com.tinyapps.data.features.transactions.mapper.TransactionListMapper
import com.tinyapps.data.features.transactions.repository.TransactionRepositoryImpl
import com.tinyapps.data.features.transactions.services.CreateTransactionApiService
import com.tinyapps.data.features.transactions.services.TransactionApiService
import com.tinyapps.domain.features.transactions.repository.TransactionRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {

    factory {
        TransactionListMapper()
    }
    factory {
        AccountInfoMapper()
    }
    factory<Interceptor> {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    factory {
        val chuckerCollector = ChuckerCollector(
            context = get(),
            // Toggles visibility of the push notification
            showNotification = true,
            // Allows to customize the retention period of collected data
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
        val chuckerInterceptor = ChuckerInterceptor(
            context = get(),
            collector = chuckerCollector,
            maxContentLength = 250000L,
            headersToRedact = setOf("Auth-Token")
        )

        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
            .addInterceptor(chuckerInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    factory<TransactionApiService> {
        createRetrofitWithGson("https://spreadsheets.google.com").create(
            TransactionApiService::class.java
        )
    }

    factory<CreateTransactionApiService> {
        createRetrofit("https://docs.google.com").create(
            CreateTransactionApiService::class.java
        )
    }

    single<TransactionRepository> {
        TransactionRepositoryImpl(
            transactionApiService = get(),
            createTransactionApiService = get(),
            remoteExceptionInterceptor = get(),
            transactionListMapper = get(),
            accountInfoMapper = get()
        )
    }
    single { RemoteExceptionInterceptor() }
}

private fun Scope.createRetrofitWithGson(baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .client(get())
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun Scope.createRetrofit(baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .client(get())
        .baseUrl(baseUrl)
        .build()
}