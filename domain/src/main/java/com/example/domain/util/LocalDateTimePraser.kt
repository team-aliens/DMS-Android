package com.example.domain.util

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

fun String.toLocalDateTime(): LocalDateTime =
    LocalDateTime.parse(this, formatter)

fun String.toDate(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA)
    val subTime = dateFormat.parse(this)
    val date = Date(System.currentTimeMillis())
    val currentTime = dateFormat.format(date)
    val getTime = dateFormat.format(subTime)
    val longCurrentTime = dateFormat.parse(currentTime).time
    val longGetTime = dateFormat.parse(getTime).time
    val diff = (longCurrentTime - longGetTime) / 1000
    val dayDiff = (diff / 86400)
    var later = ""
    if (dayDiff < 0 || dayDiff >= 31) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        later = dateFormat.format(subTime)
    } else {
        if (dayDiff <= 0) {
            when (diff) {
                in 0..60 ->
                    later = "방금"
                in 61..120 -> later = "1분전"
                in 121..3600 ->
                    later = "${diff / 60}분 전"
                in 3601..7200 -> later = "1시간 전"
                else -> later = "${diff / 3600}시간 전"
            }
        } else {
            when (dayDiff) {
                1.toLong() -> later = "어제"
                in 2..6 -> later = "${dayDiff}일 전"
                else -> later = "${dayDiff / 7}주 전"
            }
        }
    }
    return later
}