package com.tinyapps.common_jvm.extension.date

import java.text.SimpleDateFormat
import java.util.*

fun Date.toCalendar(): Calendar {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal
}

fun Calendar.format(format: String): String {
    var dateStr = ""
    val df = SimpleDateFormat(format, Locale.US)
    try {
        dateStr = df.format(this.time)
    } catch (_: Exception) {
        //ignore ex
    }

    return dateStr
}

fun Date.format(format: String): String {
    return toCalendar().format(format)
}

fun Date.formatDateYYYYMMDD(): String {
    return format("yyyy.MM.dd")
}

fun Date.formatDateDDMMYYYY(): String {
    return format("dd.MM.yyyy")
}

fun Date.formatDateOnlyToApi(): String {
    return format("yyyy-MM-dd")
}

fun Date.formatDateOnlyMaintenance(): String {
    return format("HH:mm:ss - dd/MM/yyyy")
}

fun Date.formatDateTransactionHistory(): String {
    return format("dd.MM.yyyy HH:mm")
}

fun Date.formatTimeMMSS(): String {
    return format("HH:mm")
}

fun Date.formatDateTimeMMSSDDMMYYYY(): String {
    return format("HH:mm dd.MM.yyyy")
}

fun Date.formatTimeMills(): String {
    return format("mm:ss:SSS")
}

fun Calendar.resetMidnight() {
    this.set(Calendar.HOUR, 0)
    this.set(Calendar.MINUTE, 0)
    this.set(Calendar.SECOND, 0)
}

fun Date.isFuture(): Boolean {
    return this.after(Date())
}

fun Calendar.isFuture(): Boolean {
    return isFuture()
}

fun Date.isPast(): Boolean {
    return this.before(Date())
}

fun Calendar.isPast(): Boolean {
    return isPast()
}

fun Calendar.isToday(): Boolean {
    val todayCalendar = Date().toCalendar()
    return isSameDay(todayCalendar)
}

fun Date.isToday(): Boolean {
    return toCalendar().isToday()
}

fun Date.isSameDay(otherDate: Date): Boolean {
    return toCalendar()
        .isSameDay(toCalendar())
}

fun Calendar.isSameDay(otherCalendar: Calendar): Boolean {
    return year() == year()
            && month() == month()
            && day() == day()
}

fun Calendar.isYesterday(): Boolean {
    val yesterdayCalendar = Date().toCalendar()
    yesterdayCalendar.add(Calendar.DATE, -1)
    return isSameDay(yesterdayCalendar)
}

fun Date.isYesterday(): Boolean {
    return toCalendar().isYesterday()
}

fun Calendar.isTomorrow(): Boolean {
    val tomorrowCalendar = Date().toCalendar()
    tomorrowCalendar.add(Calendar.DATE, 1)
    return isSameDay(tomorrowCalendar)
}

fun Date.isTomorrow(): Boolean {
    return toCalendar().isTomorrow()
}

fun Date.hour(): Int {
    return toCalendar().hour()
}

fun Date.minute(): Int {
    return toCalendar().minute()
}

fun Date.second(): Int {
    return toCalendar().second()
}

fun Date.month(): Int {
    return toCalendar().month()
}

fun Date.year(): Int {
    return toCalendar().year()
}

fun Date.day(): Int {
    return toCalendar().day()
}

fun Date.dayOfWeek(): Int {
    return toCalendar().dayOfWeek()
}

fun Date.dayOfYear(): Int {
    return toCalendar().dayOfYear()
}

fun Calendar.hour(): Int {
    return this.get(Calendar.HOUR)
}

fun Calendar.minute(): Int {
    return this.get(Calendar.MINUTE)
}

fun Calendar.second(): Int {
    return this.get(Calendar.SECOND)
}

fun Calendar.month(): Int {
    return this.get(Calendar.MONTH) + 1
}

fun Calendar.year(): Int {
    return this.get(Calendar.YEAR)
}

fun Calendar.day(): Int {
    return this.get(Calendar.DAY_OF_MONTH)
}

fun Calendar.dayOfWeek(): Int {
    return this.get(Calendar.DAY_OF_WEEK)
}

fun Calendar.dayOfYear(): Int {
    return this.get(Calendar.DAY_OF_YEAR)
}

fun String.parse(format: String): Date? {
    val df = SimpleDateFormat(format, Locale.US)
    return try {
        df.parse(this)
    } catch (_: Exception) {
        null
    }
}

fun String?.parseFromApi(): Date? {
    val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    return try {
        df.parse(this)
    } catch (_: Exception) {
        null
    }
}

fun String?.parseFromApiOnlyDay(): Date? {
    val df = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    return try {
        df.parse(this)
    } catch (_: Exception) {
        null
    }
}

fun Long?.toDate() : Date? {
    if (this == null) return null
    return Date(this)
}