package com.tinyapps.data.features.transactions.di

import com.comicoth.remote.exception_interceptor.RemoteExceptionInterceptor
import com.tinyapps.data.features.transactions.mapper.TransactionListMapper
import com.tinyapps.data.features.transactions.repository.TransactionRepositoryImpl
import com.tinyapps.data.features.transactions.services.TransactionApiService
import com.tinyapps.domain.features.transactions.repository.TransactionRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {

    factory {
        TransactionListMapper()
    }
    factory<Interceptor> {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    factory {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl("https://spreadsheets.google.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    factory { get<Retrofit>().create(TransactionApiService::class.java) }


    single<TransactionRepository> {
        TransactionRepositoryImpl(
            transactionApiService = get(),
            transactionListMapper = get()
        )
    }
    single { RemoteExceptionInterceptor() }
}