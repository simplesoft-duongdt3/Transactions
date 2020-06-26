package com.tinyapps.data.features.transactions.mapper

import com.tinyapps.common_jvm.mapper.Mapper
import com.tinyapps.domain.features.transactions.models.TransactionListEntity
import com.tinyapps.presentation.features.transactions.model.Transaction

class TransactionMapper : Mapper< TransactionListEntity.Transaction,Transaction>() {
    override fun map(input: TransactionListEntity.Transaction): Transaction {
        return Transaction(id = input.id,name = input.name,date = input.date,amount = input.value,comment = input.description,tags = input.tags)
    }
}