package com.tinyapps.data.features.transactions.repository

import com.tinyapps.common_jvm.exception.Failure
import com.tinyapps.common_jvm.functional.Either
import com.tinyapps.data.features.transactions.mapper.TransactionListMapper
import com.tinyapps.data.features.transactions.services.TransactionApiService
import com.tinyapps.domain.features.transactions.models.TransactionListEntity
import com.tinyapps.domain.features.transactions.repository.TransactionRepository

class TransactionRepositoryImpl(private val transactionApiService: TransactionApiService, private val transactionListMapper: TransactionListMapper) : TransactionRepository {
    override suspend fun getTransactions(): Either<Failure, TransactionListEntity>  = Either.runSuspendWithCatchError(listOf()) {
        val transactionsListResponse = transactionApiService.getTransactionList()
        return@runSuspendWithCatchError Either.Success(transactionListMapper.map(transactionsListResponse))
    }
}