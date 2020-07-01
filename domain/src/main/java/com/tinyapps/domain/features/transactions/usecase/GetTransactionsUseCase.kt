package com.tinyapps.domain.features.transactions.usecase

import com.tinyapps.common_jvm.exception.Failure
import com.tinyapps.common_jvm.functional.Either
import com.tinyapps.domain.base.usecase.UseCase
import com.tinyapps.domain.base.usecase.UseCaseParams
import com.tinyapps.domain.features.transactions.models.TransactionListEntity
import com.tinyapps.domain.features.transactions.repository.TransactionRepository

class GetTransactionsUseCase(private val transactionRepository: TransactionRepository) :
    UseCase<GetTransactionsUseCaseParams, TransactionListEntity>() {
    override suspend fun executeInternal(params: GetTransactionsUseCaseParams): Either<Failure, TransactionListEntity> {
        return transactionRepository.getTransactions(
            tags = params.tags,
            type = params.type,
            limitAmount = params.limitAmount
        )
    }
}

data class GetTransactionsUseCaseParams(
    val tags: List<String>,
    val type: String,
    val limitAmount: Double
) : UseCaseParams