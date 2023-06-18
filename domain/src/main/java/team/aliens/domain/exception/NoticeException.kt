package team.aliens.domain.exception

sealed class NoticeException(
    message: String,
) : RuntimeException(message) {

    object NoticeNotFound : NoticeException("Notice not found")
}
