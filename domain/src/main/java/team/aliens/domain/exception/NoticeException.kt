package team.aliens.domain.exception

sealed class NoticeException(
    message: String,
    val code: Int,
) : RuntimeException(message) {

    object NoticeNotFound : NoticeException(
        message = "Notice not found",
        code = 404,
    )
}
