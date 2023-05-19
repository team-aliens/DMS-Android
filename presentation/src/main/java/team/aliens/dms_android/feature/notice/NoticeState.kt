package team.aliens.dms_android.feature.notice

import team.aliens.dms_android._base.UiState
import team.aliens.dms_android.base.MviState
import team.aliens.domain._model._common.Order
import team.aliens.domain.entity.notice.NoticeListEntity
import team.aliens.domain.enums.NoticeListSCType

data class NoticeState(
    var type: Order = Order.NEW,
    var noticeListEntity: NoticeListEntity? = null,
    var noticeDetail: NoticeDetail = NoticeDetail(),
    var hasNewNotice: Boolean = false,
) : UiState {
    companion object {
        fun initial() = NoticeState(
            type = Order.NEW,
            noticeListEntity = null,
        )
    }
}

data class NoticeDetail(
    var title: String = "",
    var content: String = "",
    var createAt: String = "",
)