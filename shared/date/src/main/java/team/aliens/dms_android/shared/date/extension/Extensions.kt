package team.aliens.dms_android.shared.date.extension

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

fun Long.toLocalDateTime(zone: ZoneOffset = ZoneOffset.UTC): LocalDateTime =
    LocalDateTime.ofInstant(Instant.ofEpochMilli(this), zone)

fun Long.toLocalDate(): LocalDate = LocalDate.ofEpochDay(this)
