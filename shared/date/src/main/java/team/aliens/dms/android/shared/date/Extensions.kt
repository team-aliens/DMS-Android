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

fun LocalDate.toDate(): Date =
    Date.from(this.atStartOfDay().toInstant(ZoneOffset.UTC))

fun LocalDateTime.toDate(): Date =
    Date.from(this.toInstant(ZoneOffset.UTC))

fun LocalDateTime.toElapsedText(now: LocalDateTime): String {
    val seconds = ChronoUnit.SECONDS.between(this, now)
    val minutes = ChronoUnit.MINUTES.between(this, now)
    val hours = ChronoUnit.HOURS.between(this, now)
    val days = ChronoUnit.DAYS.between(this, now)
    val months = ChronoUnit.MONTHS.between(this, now)
    val years = ChronoUnit.YEARS.between(this, now)

    return when {
        seconds < 60 -> "${seconds}초 전"
        minutes < 60 -> "${minutes}분 전"
        hours < 24 -> "${hours}시간 전"
        days < 30 -> "${days}일 전"
        months < 12 -> "${months}달 전"
        else -> "${years}년 전"
    }
}
