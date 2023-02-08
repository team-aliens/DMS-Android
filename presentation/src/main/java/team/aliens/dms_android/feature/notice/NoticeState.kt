package team.aliens.dms_android.feature.notice

import com.example.dms_android.base.MviState
import com.example.domain.entity.notice.NoticeListEntity
import com.example.domain.enums.NoticeListSCType

data class NoticeState(
    var type: NoticeListSCType = NoticeListSCType.NEW,
    var noticeListEntity: NoticeListEntity? = null,
    var noticeDetail: NoticeDetail = NoticeDetail(),
) : MviState {
    companion object {
        fun initial() =
            NoticeState(
                type = NoticeListSCType.NEW,
                noticeListEntity = null,
            )
    }
}

data class NoticeDetail(
    var title: String = "",
    var content: String = "",
    var createAt: String = ""
)