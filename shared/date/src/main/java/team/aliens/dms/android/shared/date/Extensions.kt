package team.aliens.dms.android.shared.date

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

fun LocalDateTime.toEpochMilli(zone: ZoneOffset = ZoneOffset.UTC): Long =
    this.toInstant(zone).toEpochMilli()

fun Long.toLocalDateTime(zone: ZoneOffset = ZoneOffset.UTC): LocalDateTime =
    LocalDateTime.ofInstant(Instant.ofEpochMilli(this), zone)

fun Long.toLocalDate(): LocalDate = LocalDate.ofEpochDay(this)

fun String.toLocalDateTime(format: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME): LocalDateTime =
    LocalDateTime.parse(this, format)

fun String.toLocalDate(format: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE): LocalDate =
    LocalDate.parse(this, format)

fun LocalDate.toDate(): Date = Calendar.getInstance().apply {
    set(
        this@toDate.year,
        this@toDate.monthValue,
        this@toDate.dayOfMonth,
    )
}.time

fun LocalDateTime.toDate(): Date = Calendar.getInstance().apply {
    set(
        this@toDate.year,
        this@toDate.monthValue,
        this@toDate.dayOfMonth,
        this@toDate.hour,
        this@toDate.minute,
        this@toDate.second,
    )
}.time
