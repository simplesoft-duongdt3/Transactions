package com.tinyapps.data.features.transactions.repository

import com.tinyapps.common_jvm.date.day
import com.tinyapps.common_jvm.date.month
import com.tinyapps.common_jvm.date.year
import com.tinyapps.common_jvm.exception.Failure
import com.tinyapps.common_jvm.functional.Either
import com.tinyapps.data.features.transactions.exception_interceptor.RemoteExceptionInterceptor
import com.tinyapps.data.features.transactions.mapper.AccountInfoMapper
import com.tinyapps.data.features.transactions.mapper.TransactionListFilter
import com.tinyapps.data.features.transactions.models.TransactionListResponseWithFilter
import com.tinyapps.data.features.transactions.services.CreateTransactionApiService
import com.tinyapps.data.features.transactions.services.TransactionApiService
import com.tinyapps.domain.features.transactions.models.AccountEntity
import com.tinyapps.domain.features.transactions.repository.TransactionRepository
import java.util.*

class TransactionRepositoryImpl(
    private val transactionApiService: TransactionApiService,
    private val createTransactionApiService: CreateTransactionApiService,
    private val remoteExceptionInterceptor: RemoteExceptionInterceptor,
    private val transactionListFilter: TransactionListFilter,
    private val accountInfoMapper: AccountInfoMapper
) : TransactionRepository {

    override suspend fun getTransactions(
        tags: List<String>,
        type: String,
        limitAmount: Double,
        accountID: String
    ) = Either.runSuspendWithCatchError(listOf(remoteExceptionInterceptor)) {
        val transactionsListResponse = transactionApiService.getTransactionList()
        //filter offline
        val transactionsWithFilter = transactionsListResponse?.let {
            TransactionListResponseWithFilter(
                response = it,
                type = type,
                tags = tags,
                limitAmount = limitAmount
            )
        }
        return@runSuspendWithCatchError Either.Success(
            transactionListFilter.map(transactionsWithFilter)
        )
    }

    override suspend fun createTransaction(
        name: String,
        amount: Double,
        description: String,
        tags: List<String>,
        date: Date,
        accountID: String
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
            tags = stringTags,
            accountID = accountID
        )
        return@runSuspendWithCatchError Either.Success(true)
    }

    override suspend fun getAccountInfo(): Either<Failure, List<AccountEntity>> =
        Either.runSuspendWithCatchError(listOf(remoteExceptionInterceptor)) {
            val accountInfoResponse = transactionApiService.getAccountInfo()
            return@runSuspendWithCatchError Either.Success(
                accountInfoMapper.mapList(
                    accountInfoResponse?.feed?.accounts
                )
            )
        }
}