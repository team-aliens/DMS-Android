package team.aliens.dms_android.feature.notice

import team.aliens.dms_android.base.MviState
import team.aliens.domain._model._common.Order
import team.aliens.domain._model.notice.FetchNoticesOutput

data class NoticeState(
    var type: Order = Order.NEW,
    var noticeListEntity: FetchNoticesOutput? = null,
    var noticeDetail: NoticeDetail = NoticeDetail(),
    var hasNewNotice: Boolean = false,
) : MviState {
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