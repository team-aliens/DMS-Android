package team.aliens.dms_android.feature.notice

import team.aliens.dms_android.base.MviState
import team.aliens.domain.entity.notice.NoticeListEntity
import team.aliens.domain.enums.NoticeListSCType

data class NoticeState(
    var type: NoticeListSCType = NoticeListSCType.NEW,
    var noticeListEntity: NoticeListEntity? = null,
    var noticeDetail: NoticeDetail = NoticeDetail(),
) : MviState {
    companion object {
        fun initial() = NoticeState(
            type = NoticeListSCType.NEW,
            noticeListEntity = null,
        )
    }
}

data class NoticeDetail(
    var title: String = "",
    var content: String = "",
    var createAt: String = "",
)