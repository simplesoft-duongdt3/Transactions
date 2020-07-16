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

data class AccountGsxName (
    @SerializedName("\$t") val value : String
)