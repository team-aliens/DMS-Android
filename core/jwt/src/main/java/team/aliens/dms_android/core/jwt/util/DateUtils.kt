// TODO: move files to shared module

package team.aliens.dms_android.core.jwt.util

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

fun LocalDateTime.toEpochMillis(zone: ZoneOffset = ZoneOffset.UTC): Long =
    this.toInstant(zone).toEpochMilli()

fun Long.toLocalDateTime(zone: ZoneOffset = ZoneOffset.UTC): LocalDateTime =
    LocalDateTime.ofInstant(Instant.ofEpochMilli(this), zone)

fun LocalDate.toEpochMillis(zone: ZoneOffset = ZoneOffset.UTC): Long = this.toEpochDay()

fun Long.toLocalDate(): LocalDate = LocalDate.ofEpochDay(this)
