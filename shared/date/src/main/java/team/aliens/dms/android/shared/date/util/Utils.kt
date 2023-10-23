package team.aliens.dms.android.shared.date.util

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val today: LocalDate
    inline get() = LocalDate.now()

val now: LocalDateTime
    inline get() = LocalDateTime.now()

fun dateOf(
    year: Int,
    month: Int,
    dayOfMonth: Int,
): Date {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.parse("$year-${month + 1}-$dayOfMonth")!!
}
