package team.aliens.dms_android.feature.util

fun String.extractHourFromDate(): String {
    return this.substring(0, 5)
}
