package team.aliens.dms_android.feature.notice

import team.aliens.dms_android._base.MviEvent

sealed class NoticeEvent : MviEvent {
    data class CheckNewNotice(val hasNewNotice: Boolean): NoticeEvent()
}
