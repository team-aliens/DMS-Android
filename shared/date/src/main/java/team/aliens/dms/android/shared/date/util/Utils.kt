package team.aliens.dms.android.shared.date.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

val today: LocalDate
    inline get() = LocalDate.now()

val now: LocalDateTime
    inline get() = LocalDateTime.now()

val timeNow: LocalTime
    inline get() = LocalTime.now()
