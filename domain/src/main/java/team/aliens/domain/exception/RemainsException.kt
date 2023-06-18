package team.aliens.domain.exception

sealed class RemainsException(
    message: String,
    val code: Int,
) : RuntimeException(message) {

    object NotRemainsApplicationTime : RemainsException(
        message = "Not remains application time",
        code = 403,
    )

    object RemainsNotApplied : RemainsException(
        message = "Remains not applied",
        code = 404,
    )

    object RemainsApplicationPeriodNotSet : RemainsException(
        message = "No remains application period set",
        code = 404,
    )
}
