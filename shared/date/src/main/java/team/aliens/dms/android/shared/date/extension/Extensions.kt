package team.aliens.dms.android.shared.date.extension

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

fun LocalDateTime.toEpochMilli(zone: ZoneOffset = ZoneOffset.UTC): Long =
    this.toInstant(zone).toEpochMilli()

fun Long.toLocalDateTime(zone: ZoneOffset = ZoneOffset.UTC): LocalDateTime =
    this.toLocalDateTime(zone)

fun Long.toLocalDate(): LocalDate = LocalDate.ofEpochDay(this)
