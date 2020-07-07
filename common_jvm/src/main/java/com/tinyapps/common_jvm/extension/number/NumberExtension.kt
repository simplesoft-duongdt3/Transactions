package com.tinyapps.common_jvm.extension.number

import com.tinyapps.common_jvm.extension.date.format
import com.tinyapps.common_jvm.extension.date.formatDateDDMMYYYY
import com.tinyapps.common_jvm.extension.date.toCalendar
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

val numberFormat = DecimalFormat("#,##0", DecimalFormatSymbols.getInstance(Locale.ENGLISH).apply {
    groupingSeparator = ','
    decimalSeparator = '.'
})

fun Int.format(): String {
    return numberFormat.format(this)
}

fun Double.format(): String {
    return numberFormat.format(this)
}

fun Float.format(): String {
    return numberFormat.format(this)
}

fun Long.format(): String {
    return numberFormat.format(this)
}

fun Long.formatDateToString(): String {
    val cal = Calendar.getInstance()
    cal.timeInMillis = this
    val date = cal.time
    return date.format("DD/MM/yyyy")
}