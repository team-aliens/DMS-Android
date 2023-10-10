package team.aliens.dms_android.domain.exception

sealed class NoticeException(
    message: String,
) : RuntimeException(message) {

    object NoticeNotFound : team.aliens.dms_android.domain.exception.NoticeException("Notice not found")
}
