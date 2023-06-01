package team.aliens.dms_android.feature.notice

import java.util.UUID
import team.aliens.dms_android._base.UiState
import team.aliens.domain._model._common.Order
import team.aliens.domain._model.notice.FetchNoticesOutput
import team.aliens.domain._model.notice.Notice

internal data class NoticesUiState(
    val type: Order,
    val notices: List<FetchNoticesOutput.NoticeInformation>,
    val notice: Notice,
    val newNoticesExists: Boolean = false,
    val order: Order,
    val noticeId: UUID?,
    val error: Throwable?,
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
            newNoticesExists = false,
            order = Order.NEW,
            noticeId = null,
            error = null,
        )
    }
}