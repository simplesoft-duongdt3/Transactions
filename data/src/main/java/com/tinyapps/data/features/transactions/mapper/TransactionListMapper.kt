package com.tinyapps.data.features.transactions.mapper

import com.tinyapps.common_jvm.extension.nullable.defaultEmpty
import com.tinyapps.common_jvm.mapper.Mapper
import com.tinyapps.data.features.transactions.models.TransactionListResponse
import com.tinyapps.domain.features.transactions.models.TransactionListEntity

class TransactionListMapper : Mapper<TransactionListResponse?, TransactionListEntity>() {
    override fun map(input: TransactionListResponse?): TransactionListEntity {
        val transactions = input?.transactions?.filterNotNull().defaultEmpty().map { transaction ->
            TransactionListEntity.Transaction(
                id = transaction.id,
                date = transaction.date,
                description = transaction.description,
                name = transaction.name,
                tags = transaction.tags,
                value = transaction.value
            )
        }
        return TransactionListEntity(transactions = transactions)
    }
}