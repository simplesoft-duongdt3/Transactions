package com.tinyapps.common_jvm.extension.string

import com.tinyapps.common_jvm.extension.nullable.defaultZero
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ChuTien on ${1/25/2017}.
 */


val numberFormat = DecimalFormat("#,##0", DecimalFormatSymbols.getInstance(Locale.ENGLISH).apply {
    groupingSeparator = ','
    decimalSeparator = '.'
})

@Suppress("SimpleDateFormat")
val dateFormat = SimpleDateFormat("dd/MM/yyyy")

/**
 * Get list tags from json String from Transaction API
 */
fun String.toListTags() : List<String>{
    return this.split(", ")
}

fun String.toDateLong() : Long{
    return dateFormat.parse(this).time.defaultZero()
}

fun String.moneyToDouble() : Double{
    return numberFormat.parse(this).toDouble()
}