package com.tinyapps.domain.features.transactions.usecase

import com.tinyapps.common_jvm.exception.Failure
import com.tinyapps.common_jvm.functional.Either
import com.tinyapps.domain.base.usecase.UseCase
import com.tinyapps.domain.base.usecase.UseCaseParams
import com.tinyapps.domain.features.transactions.models.TransactionListEntity
import com.tinyapps.domain.features.transactions.repository.TransactionRepository
import java.util.*

class CreateTransactionUseCase(private val transactionRepository: TransactionRepository) :
    UseCase<CreateTransactionUseCaseParams, Boolean>() {
    override suspend fun executeInternal(params: CreateTransactionUseCaseParams): Either<Failure, Boolean> {
        return transactionRepository.createTransaction(
            name = params.name,
            tags = params.tags,
            description = params.description,
            amount = params.amount,
            date = params.date,
            accountID = params.accountId
        )
    }
}

data class CreateTransactionUseCaseParams(
    val name: String,
    val amount: Double,
    val description: String,
    val tags: List<String>,
    val date: Date,
    val accountId: String
) : UseCaseParams