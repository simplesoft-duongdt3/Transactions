package com.tinyapps.common_jvm.extension.string

import com.tinyapps.common_jvm.extension.nullable.defaultZero
import java.text.*
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
    return try {
        dateFormat.parse(this).time.defaultZero()
    } catch (e: ParseException) {
        0
    }
}

fun String.moneyToDouble() : Double{
    return try {
        numberFormat.parse(this).toDouble()
    } catch (e: ParseException) {
        0.0
    }
}

fun String.isChipEmpty() : Boolean{
    return this.replace("\n","").isEmpty()
}