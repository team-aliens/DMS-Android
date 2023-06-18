package team.aliens.domain.exception

sealed class RemainsException(
    message: String,
) : RuntimeException(message) {

    object NotRemainsApplicationTime : RemainsException("Not remains application time")

    object RemainsNotApplied : RemainsException("Remains not applied")

    object RemainsApplicationPeriodNotSet : RemainsException("No remains application period set")
}
