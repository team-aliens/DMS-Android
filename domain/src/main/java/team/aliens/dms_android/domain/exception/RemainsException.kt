package team.aliens.dms_android.domain.exception

sealed class RemainsException(
    message: String,
) : RuntimeException(message) {

    object NotRemainsApplicationTime : team.aliens.dms_android.domain.exception.RemainsException("Not remains application time")

    object RemainsNotApplied : team.aliens.dms_android.domain.exception.RemainsException("Remains not applied")

    object RemainsApplicationPeriodNotSet : team.aliens.dms_android.domain.exception.RemainsException("No remains application period set")
}
