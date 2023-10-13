package team.aliens.dms.android.database.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

// fixme need to create core-common

@Deprecated("incorrect package")
@SuppressLint("SimpleDateFormat")
internal val tokenDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

@Deprecated("incorrect package")
internal fun String.toDate(
    formatter: SimpleDateFormat = tokenDateFormat,
): Date {
    return formatter.parse(this)!!
}
