package team.aliens.dms.android.feature._legacy.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

// todo move to common module
@SuppressLint("SimpleDateFormat")
internal val tokenDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

internal fun String.toDate(formatter: SimpleDateFormat = tokenDateFormat): Date {
    return formatter.parse(this)!!
}

internal typealias Now = Date

internal const val Hour: Long = 1000 * 60 * 60
internal const val Day: Long = Hour * 24
internal const val Week: Long = Day * 7

internal const val OneDay: Long = Day * 1
internal const val OneWeek: Long = OneDay * 7
