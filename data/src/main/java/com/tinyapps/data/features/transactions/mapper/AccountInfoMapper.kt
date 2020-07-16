package com.tinyapps.data.features.transactions.mapper

import android.util.Log
import com.tinyapps.common_jvm.extension.nullable.defaultEmpty
import com.tinyapps.common_jvm.extension.nullable.defaultZero
import com.tinyapps.common_jvm.extension.string.moneyToDouble
import com.tinyapps.common_jvm.mapper.Mapper
import com.tinyapps.data.features.transactions.models.AccountInfoResponse
import com.tinyapps.domain.features.transactions.models.TransactionListEntity

class AccountInfoMapper : Mapper<AccountInfoResponse?, TransactionListEntity.Account>() {
    override fun map(input: AccountInfoResponse?): TransactionListEntity.Account {
        Log.d("AccountInfoMapper", "from $input")
        val accountName =
            input?.feed?.accounts?.filterNotNull().defaultEmpty()[0].name.value.defaultEmpty()
        val accountTotal =
            input?.feed?.accounts?.filterNotNull().defaultEmpty()[0].total.value.moneyToDouble()
                .defaultZero()
        val account = TransactionListEntity.Account(name = accountName, total = accountTotal)
        Log.d("AccountInfoMapper", "to $account")
        return account
    }
}