package com.tinyapps.data.features.transactions.models

import com.google.gson.annotations.SerializedName

data class AccountInfoResponse(
    @SerializedName("feed")
    val feed: AccountFeed
)


data class AccountFeed(
    @SerializedName("entry")
    val accounts: List<Account?>
)

data class Account(
    @SerializedName("gsx\$total")
    val total: AccountGsxName,
    @SerializedName("gsx\$accountname")
    val name: AccountGsxName
)

data class AccountGsxName (
    @SerializedName("\$t") val value : String
)