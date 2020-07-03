package com.tinyapps.common_jvm.extension.string

import java.util.*

/**
 * Created by ChuTien on ${1/25/2017}.
 */

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