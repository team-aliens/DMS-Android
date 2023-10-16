package team.aliens.dms.android.design_system.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal fun makeDate(
    year: Int,
    month: Int,
    dayOfMonth: Int,
): Date {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.parse("$year-${month + 1}-$dayOfMonth")!!
}