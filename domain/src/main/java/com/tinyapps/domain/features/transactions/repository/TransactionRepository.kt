package com.tinyapps.domain.features.transactions.repository

import com.tinyapps.common_jvm.exception.Failure
import com.tinyapps.common_jvm.functional.Either
import com.tinyapps.domain.features.transactions.models.TransactionListEntity

interface TransactionRepository {
    suspend fun getTransactions(): Either<Failure, TransactionListEntity>
}