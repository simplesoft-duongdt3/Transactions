package com.tinyapps.domain.features.transactions.repository

import com.tinyapps.common_jvm.exception.Failure
import com.tinyapps.common_jvm.functional.Either
import com.tinyapps.domain.features.transactions.models.AccountEntity
import com.tinyapps.domain.features.transactions.models.TransactionListEntity
import java.util.*

interface TransactionRepository {
    suspend fun getTransactions(
        tags: List<String>,
        type: String,
        limitAmount: Double,
        accountID : String
    ): Either<Failure, TransactionListEntity>

    suspend fun createTransaction(
        name: String,
        amount: Double,
        description: String,
        tags: List<String>,
        date: Date
    ): Either<Failure, Boolean>

    suspend fun getAccountInfo(
        ): Either<Failure, List<AccountEntity>>
}