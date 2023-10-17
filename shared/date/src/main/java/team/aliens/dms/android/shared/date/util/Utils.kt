package team.aliens.dms.android.shared.date.util

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

val today: LocalDate
    inline get() = LocalDate.now()

val now: LocalDateTime
    inline get() = LocalDateTime.now()
