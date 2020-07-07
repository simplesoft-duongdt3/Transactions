package com.tinyapps.common_jvm.extension.string

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

/**
 * Created by ChuTien on ${1/25/2017}.
 */


val numberFormat = DecimalFormat("#,##0", DecimalFormatSymbols.getInstance(Locale.ENGLISH).apply {
    groupingSeparator = ','
    decimalSeparator = '.'
})

/**
 * Get list tags from json String from Transaction API
 */
fun String.toListTags() : List<String>{
    return this.split(", ")
}

fun String.toDateLong() : Long{
    val cal = Calendar.getInstance()
    return cal.timeInMillis
}

fun String.moneyToDouble() : Double{
    return numberFormat.parse(this).toDouble()
}