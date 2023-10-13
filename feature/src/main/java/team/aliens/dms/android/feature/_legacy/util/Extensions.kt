package team.aliens.dms.android.feature._legacy.util

fun String.extractHourFromDate(): String {
    return this.substring(0, 5)
}
