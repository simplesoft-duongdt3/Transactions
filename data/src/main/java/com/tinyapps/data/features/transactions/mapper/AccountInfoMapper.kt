package com.tinyapps.data.features.transactions.mapper

import android.util.Log
import com.tinyapps.common_jvm.extension.nullable.defaultEmpty
import com.tinyapps.common_jvm.extension.nullable.defaultZero
import com.tinyapps.common_jvm.extension.string.moneyToDouble
import com.tinyapps.common_jvm.mapper.Mapper
import com.tinyapps.data.features.transactions.models.Account
import com.tinyapps.domain.features.transactions.models.AccountEntity

class AccountInfoMapper :
    Mapper<Account?, AccountEntity>() {
    override fun map(input: Account?): AccountEntity {
        Log.d("AccountInfoMapper", "from $input")
        val account = AccountEntity(
            name = input?.name?.value.defaultEmpty(),
            total = input?.total?.value?.moneyToDouble().defaultZero()
                .defaultZero(),
            id = input?.id?.value.defaultEmpty()
        )
        Log.d("AccountInfoMapper", "to $account")
        return account
    }
}