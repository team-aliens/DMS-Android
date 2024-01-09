package team.aliens.dms.android.shared.date

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import java.util.Date

fun LocalDateTime.toEpochMilli(zone: ZoneOffset = ZoneOffset.UTC): EpochMillis =
    this.toInstant(zone).toEpochMilli()

fun EpochMillis.toLocalDateTime(zone: ZoneOffset = ZoneOffset.UTC): LocalDateTime =
    LocalDateTime.ofInstant(Instant.ofEpochMilli(this), zone)

fun EpochSecond.toLocalDate(zone: ZoneOffset = ZoneOffset.UTC): LocalDate =
    Instant.ofEpochSecond(this).atZone(zone).toLocalDate()

fun String.toLocalDateTime(format: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME): LocalDateTime =
    LocalDateTime.parse(this, format)

fun String.toLocalDate(format: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE): LocalDate =
    LocalDate.parse(this, format)

@Suppress("DEPRECATION")
fun LocalDate.toDate(): Date = Date(
    this@toDate.year,
    this@toDate.monthValue,
    this@toDate.dayOfMonth,
)

@Suppress("DEPRECATION")
fun LocalDateTime.toDate(): Date = Date(
    this@toDate.year,
    this@toDate.monthValue,
    this@toDate.dayOfMonth,
    this@toDate.hour,
    this@toDate.minute,
    this@toDate.second,
)
