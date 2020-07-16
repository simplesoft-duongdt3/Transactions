package com.tinyapps.data.features.transactions.models

import com.google.gson.annotations.SerializedName

data class Account(
    @SerializedName("gsx\$total")
    val total: AccountGsxName,
    @SerializedName("gsx\$accountname")
    val name: AccountGsxName,
    @SerializedName("gsx\$accountid")
    val id: AccountGsxName
)