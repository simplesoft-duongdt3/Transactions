package com.tinyapps.presentation.mapper

import com.tinyapps.common_jvm.mapper.Mapper
import com.tinyapps.domain.features.transactions.models.TransactionListEntity
import com.tinyapps.presentation.features.transactions.model.Account
import com.tinyapps.presentation.features.transactions.model.Transaction

class AccountMapper : Mapper<TransactionListEntity.Account, Account>() {
    override fun map(input: TransactionListEntity.Account): Account {
        return Account(name = input.name, total = input.total)
    }
}