package team.aliens.dms.android.core.ui.util

import org.threeten.bp.LocalDateTime

fun LocalDateTime.toDateString(): String {
    return "${this.year}년 ${this.monthValue}월 ${this.dayOfMonth}일"
}
