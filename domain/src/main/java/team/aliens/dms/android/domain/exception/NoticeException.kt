package team.aliens.dms.android.domain.exception

sealed class NoticeException(
    message: String,
) : RuntimeException(message) {

    object NoticeNotFound : NoticeException("Notice not found")
}
