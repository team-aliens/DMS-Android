package team.aliens.dms.android.domain._legacy.exception

sealed class RemainsException(
    message: String,
) : RuntimeException(message) {

    object NotRemainsApplicationTime : team.aliens.dms.android.domain._legacy.exception.RemainsException("Not remains application time")

    object RemainsNotApplied : team.aliens.dms.android.domain._legacy.exception.RemainsException("Remains not applied")

    object RemainsApplicationPeriodNotSet : team.aliens.dms.android.domain._legacy.exception.RemainsException("No remains application period set")
}
