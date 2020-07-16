package com.tinyapps.presentation.mapper

import com.tinyapps.common_jvm.mapper.Mapper
import com.tinyapps.presentation.features.transactions.model.Account

class AccountMapper : Mapper<com.tinyapps.domain.features.transactions.models.AccountEntity, Account>() {
    override fun map(input: com.tinyapps.domain.features.transactions.models.AccountEntity): Account {
        return Account(name = input.name, total = input.total,id = input.id)
    }
}