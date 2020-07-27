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
import com.tinyapps.domain.features.transactions.models.TransactionListEntity
import com.tinyapps.domain.features.transactions.repository.TransactionRepository
import java.util.*
import kotlin.math.abs

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
        //filter offline
        val transactions = transactionListMapper.map(
            transactionsListResponse
        ).transactions
        val resultFilterByType =
            when (type) {
                "Expenses" -> {
                    transactions.filter { transaction ->
                        (transaction.value < 0 && abs(
                            transaction.value
                        ) < limitAmount)
                    }
                }
                "Revenue" -> {
                    transactions.filter { transaction ->
                        (transaction.value >= 0 && abs(
                            transaction.value
                        ) < limitAmount)
                    }
                }
                else -> {
                    transactions.filter { transaction -> abs(transaction.value) < limitAmount }
                }
            }
        // filter by tag
        var resultFilterByTag: MutableList<TransactionListEntity.Transaction> = mutableListOf()
        if (tags.isNotEmpty()) {
            tags.forEach {
                resultFilterByTag.addAll(transactions.filter { transaction ->
                    transaction.tags.contains(
                        it
                    )
                })
            }
        } else {
            resultFilterByTag = resultFilterByType.toMutableList()
        }
        return@runSuspendWithCatchError Either.Success(
            TransactionListEntity(transactions = resultFilterByTag)
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
            return@runSuspendWithCatchError Either.Success(
                accountInfoMapper.mapList(
                    accountInfoResponse?.feed?.accounts
                )
            )
        }
}