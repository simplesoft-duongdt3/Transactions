package com.tinyapps.data.features.transactions.repository

import com.tinyapps.common_jvm.date.day
import com.tinyapps.common_jvm.date.month
import com.tinyapps.common_jvm.date.year
import com.tinyapps.common_jvm.exception.Failure
import com.tinyapps.common_jvm.functional.Either
import com.tinyapps.data.features.transactions.exception_interceptor.RemoteExceptionInterceptor
import com.tinyapps.data.features.transactions.mapper.AccountInfoMapper
import com.tinyapps.data.features.transactions.mapper.TransactionListMapper
import com.tinyapps.data.features.transactions.services.CreateTransactionApiService
import com.tinyapps.data.features.transactions.services.TransactionApiService
import com.tinyapps.domain.features.transactions.models.AccountEntity
import com.tinyapps.domain.features.transactions.repository.TransactionRepository
import java.util.*

class TransactionRepositoryImpl(
    private val transactionApiService: TransactionApiService,
    private val createTransactionApiService: CreateTransactionApiService,
    private val remoteExceptionInterceptor: RemoteExceptionInterceptor,
    private val transactionListMapper: TransactionListMapper,
    private val accountInfoMapper: AccountInfoMapper
) : TransactionRepository {

    override suspend fun getTransactions(
        tags: List<String>,
        type: String,
        limitAmount: Double
    ) = Either.runSuspendWithCatchError(listOf(remoteExceptionInterceptor)) {
        val transactionsListResponse = transactionApiService.getTransactionList()
        return@runSuspendWithCatchError Either.Success(
            transactionListMapper.map(
                transactionsListResponse
            )
        )
    }

    override suspend fun createTransaction(
        name: String,
        amount: Double,
        description: String,
        tags: List<String>,
        date: Date
    ) = Either.runSuspendWithCatchError(listOf(remoteExceptionInterceptor)) {
        val stringTags = tags.map {
            it.trim()
        }.filter {
            it.isNotEmpty()
        }.joinToString()

        createTransactionApiService.createTransaction(
            name = name,
            amount = amount,
            description = description,
            day = date.day(),
            month = date.month(),
            year = date.year(),
            tags = stringTags
        )
        return@runSuspendWithCatchError Either.Success(true)
    }

    override suspend fun getAccountInfo(): Either<Failure, List<AccountEntity>> =
        Either.runSuspendWithCatchError(listOf(remoteExceptionInterceptor)) {
            val accountInfoResponse = transactionApiService.getAccountInfo()
            return@runSuspendWithCatchError Either.Success(accountInfoMapper.mapList(accountInfoResponse?.feed?.accounts))
        }
}