package team.aliens.dms.android.shared.date.util

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

val today: LocalDate
    inline get() = LocalDate.now()

val now: LocalDateTime
    inline get() = LocalDateTime.now()

fun dateOf(
    year: Int,
    month: Int,
    day: Int,
): LocalDate = LocalDate.of(year, month, day)
/*LocalDate.parse(
    year.toString().padStart(4, '0')
            + '-' + month.toString().padStart(2, '0')
            + '-' + day.toString().padStart(2, '0'),
    DateTimeFormatter.ISO_DATE,
)*/
