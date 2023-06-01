package team.aliens.dms_android.feature.notice

import java.util.UUID
import team.aliens.dms_android._base.UiEvent
import team.aliens.domain._model._common.Order

internal sealed class NoticesUiEvent : UiEvent {
    object FetchNotices : NoticesUiEvent()
    object CheckHasNewNotice : NoticesUiEvent()
    class SetNoticeId(val noticeId: UUID): NoticesUiEvent()
}
