package team.aliens.data.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
internal val tokenDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

internal fun String.toDate(
    formatter: SimpleDateFormat = tokenDateFormat,
): Date {
    return formatter.parse(this)!!
}
