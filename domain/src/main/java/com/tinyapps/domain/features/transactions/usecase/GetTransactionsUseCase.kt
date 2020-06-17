package com.tinyapps.domain.features.transactions.usecase

import com.tinyapps.common_jvm.exception.Failure
import com.tinyapps.common_jvm.functional.Either
import com.tinyapps.domain.base.usecase.UseCase
import com.tinyapps.domain.base.usecase.UseCaseParams
import com.tinyapps.domain.features.transactions.models.TransactionListEntity
import com.tinyapps.domain.features.transactions.repository.TransactionRepository

class GetTransactionsUseCase(private val transactionRepository: TransactionRepository): UseCase<UseCaseParams.Empty, TransactionListEntity>() {
    override suspend fun executeInternal(params: UseCaseParams.Empty): Either<Failure, TransactionListEntity> {
        return transactionRepository.getTransactions()
    }
}