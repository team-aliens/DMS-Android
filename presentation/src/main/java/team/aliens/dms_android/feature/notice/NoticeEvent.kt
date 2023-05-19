package team.aliens.dms_android.feature.notice

import team.aliens.dms_android._base.UiEvent

sealed class NoticeEvent : UiEvent {
    data class CheckNewNotice(val hasNewNotice: Boolean): NoticeEvent()
}
