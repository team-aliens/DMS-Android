package team.aliens.remote.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
internal val tokenDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

internal fun String.toDate(
    formatter: SimpleDateFormat,
): Date {
    return formatter.parse(this)!!
}
