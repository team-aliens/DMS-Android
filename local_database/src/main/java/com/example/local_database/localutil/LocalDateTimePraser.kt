package com.example.local_database.localutil

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

fun String.toLocalDateTime(): LocalDateTime =
    LocalDateTime.parse(this, formatter)
