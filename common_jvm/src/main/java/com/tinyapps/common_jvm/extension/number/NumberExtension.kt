package com.tinyapps.common_jvm.extension.number

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

val numberFormat = DecimalFormat("#,##0", DecimalFormatSymbols.getInstance(Locale.ENGLISH).apply {
    groupingSeparator = '.'
    decimalSeparator = ','
})

fun Int.format() : String {
    return numberFormat.format(this)
}

fun Double.format() : String {
    return numberFormat.format(this)
}

fun Float.format() : String {
    return numberFormat.format(this)
}