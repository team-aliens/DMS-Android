package com.example.dms_android.feature.mypage

import com.example.dms_android.base.MviState
import com.example.domain.enums.PointType

data class MyPageState(
    val type: PointType
) : MviState {
    companion object {
        fun initial() =
            MyPageState(
                type = PointType.ALL
            )
    }
}
