package team.aliens.domain.exception

sealed class NoticeException(
    message: String,
    val code: Int,
) : RuntimeException(message) {

    object NoticesNotFound : NoticeException(
        message = "Notices not found",
        code = 404,
    )
}