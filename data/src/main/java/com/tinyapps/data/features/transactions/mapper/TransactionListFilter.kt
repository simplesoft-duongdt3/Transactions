package com.tinyapps.data.features.transactions.mapper

import com.tinyapps.common_jvm.extension.nullable.defaultEmpty
import com.tinyapps.common_jvm.extension.nullable.defaultZero
import com.tinyapps.common_jvm.mapper.Mapper
import com.tinyapps.data.features.transactions.models.TransactionListResponseWithFilter
import com.tinyapps.domain.features.transactions.models.TransactionListEntity
import kotlin.math.abs

/**
 * Created by ChuTien on ${1/25/2017}.
 */
class TransactionListFilter(private val transactionListMapper: TransactionListMapper) :
    Mapper<TransactionListResponseWithFilter?, TransactionListEntity>() {
    override fun map(input: TransactionListResponseWithFilter?): TransactionListEntity {
        val transactions = transactionListMapper.map(input = input?.response).transactions
        val limitAmount = input?.limitAmount.defaultZero()
        val resultFilterByType =
            when (input?.type.defaultEmpty()) {
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

        val tags = input?.tags.defaultEmpty()
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
        return TransactionListEntity(transactions = resultFilterByTag)
    }

}