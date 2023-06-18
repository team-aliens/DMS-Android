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
        message = "Remains not applicated",
        code = 404,
    )

    object NoRemainsApplicationOption : RemainsException(
        message = "No remains application option",
        code = 404,
    )
}
