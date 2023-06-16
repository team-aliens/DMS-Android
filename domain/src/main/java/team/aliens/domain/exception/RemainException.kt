package team.aliens.domain.exception

sealed class RemainException(
    message: String,
    val code: Int,
) : RuntimeException(message) {

    object NotRemainApplyTime : RemainException(
        message = "Not remain apply time",
        code = 403,
    )

    object DoNotRemainApply : RemainException(
        message = "Do not Remain apply",
        code = 404,
    )

    object NoRemainApplicationPeriodSet : RemainException(
        message = "No remain application period set",
        code = 404,
    )
}