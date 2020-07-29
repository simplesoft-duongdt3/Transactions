package com.tinyapps.data.features.transactions.models

/**
 * Created by ChuTien on ${1/25/2017}.
 */
data class TransactionListResponseWithFilter(
    val response: TransactionListResponse,
    val type: String,
    val tags: List<String>,
    val limitAmount: Double
)