package com.tinyapps.data.features.transactions.models

import com.google.gson.annotations.SerializedName

data class TransactionListResponse(
    @SerializedName("feed")
    val feed: Feed
)


data class Feed(
    @SerializedName("entry")
    val transactions: List<Transaction?>
)

data class Transaction(
    @SerializedName("gsx\$name")
    val name: GsxName?,
    @SerializedName("gsx\$dấuthờigian")
    val date: GsxName?,
    @SerializedName("gsx\$amount")
    val value: GsxName?,
    @SerializedName("gsx\$description")
    val description: GsxName?,
    @SerializedName("gsx\$tags")
    val tags: GsxName?,
    @SerializedName("gsx\$total")
    val total: GsxName?,
    @SerializedName("gsx\$accountid")
    val accountID: GsxName?
)

data class GsxName (
    @SerializedName("\$t") val value : String
)