package team.aliens.dms_android.feature.notice

import java.util.UUID
import team.aliens.dms_android._base.UiEvent
import team.aliens.domain._model._common.Order

internal sealed class NoticesUiEvent : UiEvent {
    class FetchNotices(val order: Order) : NoticesUiEvent()
    object CheckHasNewNotice : NoticesUiEvent()
    object SetNoticeOrder: NoticesUiEvent()
    class SetNoticeId(val noticeId: UUID): NoticesUiEvent()
}
