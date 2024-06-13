package team.aliens.dms.android.shared.date.util

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

val today: LocalDate
    inline get() = LocalDate.now()

val now: LocalDateTime
    inline get() = LocalDateTime.now()

val timeNow: LocalTime
    inline  get() = LocalTime.now()
