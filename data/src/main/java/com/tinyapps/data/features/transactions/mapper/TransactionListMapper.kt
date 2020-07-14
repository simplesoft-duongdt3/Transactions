package com.tinyapps.data.features.transactions.mapper

import android.util.Log
import com.tinyapps.common_jvm.extension.nullable.defaultEmpty
import com.tinyapps.common_jvm.extension.nullable.defaultZero
import com.tinyapps.common_jvm.extension.string.moneyToDouble
import com.tinyapps.common_jvm.extension.string.toDateLong
import com.tinyapps.common_jvm.extension.string.toListTags
import com.tinyapps.common_jvm.mapper.Mapper
import com.tinyapps.data.features.transactions.models.TransactionListResponse
import com.tinyapps.domain.features.transactions.models.TransactionListEntity

class TransactionListMapper : Mapper<TransactionListResponse?,TransactionListEntity>() {
    override fun map(input: TransactionListResponse?): TransactionListEntity {
        Log.d("TransactionListMapper","from $input")
        val account = TransactionListEntity.Account("Account X",0.0)
        val transactions = input?.feed?.transactions?.filterNotNull().defaultEmpty().filter { transaction -> transaction.name.value.isNullOrEmpty().not() }.mapIndexed { index,transaction ->
            Log.d("TransactionListMapper","process $transaction")
            if(index == 0) {
                //first item will contain total money of account
                account.total = transaction.total.value.moneyToDouble()
            }
            TransactionListEntity.Transaction(
                id = "",
                date = transaction.date.value.toDateLong(),
                description = transaction.description.value,
                name = transaction.name.value,
                tags = transaction.tags.value.toListTags(),
                value = transaction.value.value.toDouble().defaultZero(),
                total = transaction.total.value.moneyToDouble()
            )
        }

        Log.d("TransactionListMapper","to $transactions")
        return TransactionListEntity(transactions = transactions,account = account)
    }
}