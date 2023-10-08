package team.aliens.dms_android.util

fun String.extractHourFromDate(): String {
    return this.substring(0, 5)
}
