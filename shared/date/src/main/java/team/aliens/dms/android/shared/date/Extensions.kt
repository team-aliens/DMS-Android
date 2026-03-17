package team.aliens.dms.android.shared.date

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
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

fun LocalDateTime.toElapsedText(now: LocalDateTime): String {
    val seconds = ChronoUnit.SECONDS.between(this, now)
    if (seconds < 60) return "${seconds}초 전"

    val minutes = ChronoUnit.MINUTES.between(this, now)
    if (minutes < 60) return "${minutes}분 전"

    val hours = ChronoUnit.HOURS.between(this, now)
    if (hours < 24) return "${hours}시간 전"

    val days = ChronoUnit.DAYS.between(this, now)
    if (days < 30) return "${days}일 전"

    val months = ChronoUnit.MONTHS.between(this, now)
    return if (months < 12) "${months}달 전" else "${ChronoUnit.YEARS.between(this, now)}년 전"
}
