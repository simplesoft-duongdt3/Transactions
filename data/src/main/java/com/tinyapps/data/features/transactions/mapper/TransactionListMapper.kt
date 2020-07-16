package com.tinyapps.data.features.transactions.mapper

import android.util.Log
import com.tinyapps.common_jvm.extension.nullable.defaultEmpty
import com.tinyapps.common_jvm.extension.nullable.defaultZero
import com.tinyapps.common_jvm.extension.string.toDateLong
import com.tinyapps.common_jvm.extension.string.toListTags
import com.tinyapps.common_jvm.mapper.Mapper
import com.tinyapps.data.features.transactions.models.TransactionListResponse
import com.tinyapps.domain.features.transactions.models.AccountEntity
import com.tinyapps.domain.features.transactions.models.TransactionListEntity

class TransactionListMapper : Mapper<TransactionListResponse?,TransactionListEntity>() {
    override fun map(input: TransactionListResponse?): TransactionListEntity {
        Log.d("TransactionListMapper","from $input")
        val transactions = input?.feed?.transactions?.filterNotNull().defaultEmpty().filter { transaction -> transaction.name.value.isEmpty().not() }.mapIndexed { index, transaction ->
            Log.d("TransactionListMapper","process $transaction")
            TransactionListEntity.Transaction(
                id = "",
                date = transaction.date.value.toDateLong(),
                description = transaction.description.value,
                name = transaction.name.value,
                tags = transaction.tags.value.toListTags(),
                value = transaction.value.value.toDouble().defaultZero()
            )
        }

        Log.d("TransactionListMapper","to $transactions")
        return TransactionListEntity(transactions = transactions)
    }
}