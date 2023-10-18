package team.aliens.dms.android.domain._legacy.exception

sealed class NoticeException(
    message: String,
) : RuntimeException(message) {

    object NoticeNotFound : team.aliens.dms.android.domain._legacy.exception.NoticeException("Notice not found")
}
