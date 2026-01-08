package team.aliens.dms.android.core.ui.util

import java.time.LocalDateTime

fun LocalDateTime.toDateString(): String {
    return "${this.year}년 ${this.monthValue}월 ${this.dayOfMonth}일"
}

fun LocalDateTime.toDateTimeString(): String =
    "${this.year}.${this.monthValue}.${this.dayOfMonth}. ${if (this.hour > 12) "오후" else "오전"} ${this.hour}시 ${this.minute}분"
