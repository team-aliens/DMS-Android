package team.aliens.domain.exception

sealed class RemainsException(
    message: String,
    val code: Int,
) : RuntimeException(message) {

    object NotRemainsApplyTime : RemainsException(
        message = "Not remains application time",
        code = 403,
    )

    object DoNotRemainsApply : RemainsException(
        message = "Do not remains apply",
        code = 404,
    )

    object NoRemainsApplicationPeriodSet : RemainsException(
        message = "No remains application period set",
        code = 404,
    )
}
