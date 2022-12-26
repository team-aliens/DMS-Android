package com.example.dms_android.feature.notice

import com.example.dms_android.base.MviState
import com.example.domain.enums.NoticeListSCType
import com.example.domain.enums.PointType

data class NoticeState(
    var type: NoticeListSCType
) : MviState {
    companion object {
        fun initial() =
            NoticeState(
                type = NoticeListSCType.NEW
            )
    }
}
