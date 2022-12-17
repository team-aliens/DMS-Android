package com.example.local_database.util

import java.time.LocalDateTime

fun String.toLocalDateTime(): LocalDateTime =
    LocalDateTime.parse(this)