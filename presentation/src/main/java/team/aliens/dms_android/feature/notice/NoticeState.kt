package team.aliens.dms_android.feature.notice

import java.util.UUID
import team.aliens.dms_android._base.UiState
import team.aliens.dms_android.base.MviState
import team.aliens.domain._model._common.Order
import team.aliens.domain._model.notice.FetchNoticesOutput
import team.aliens.domain._model.notice.Notice
import team.aliens.domain.entity.notice.NoticeListEntity
import team.aliens.domain.enums.NoticeListSCType

internal data class NoticesUiState(
    val type: Order,
    val notices: List<FetchNoticesOutput.NoticeInformation>,
    val notice: Notice,
    val hasNewNotice: Boolean = false,
    val order: Order,
    val noticeId: UUID?,
    val noticeErrorMessage: String,
) : UiState {
    companion object {
        fun initial() = NoticesUiState(
            type = Order.NEW,
            notices = emptyList(),
            notice = Notice(
                id = UUID.randomUUID(),
                title = "",
                content = "",
                createdAt = "",
            ),
            hasNewNotice = false,
            order = Order.NEW,
            noticeId = null,
            noticeErrorMessage = "",
        )
    }
}