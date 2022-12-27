package com.example.dms_android.feature.mypage

import com.example.dms_android.base.MviState
import com.example.domain.enums.PointType

data class MyPageState(
    val type: PointType,
    var totalPoint: Int,
    val myPageEntity: MyPageEntity = MyPageEntity(),
) : MviState {
    companion object {
        fun initial() =
            MyPageState(
                type = PointType.ALL,
                totalPoint = 0
            )
    }
}

data class MyPageEntity(
    var schoolName: String = "",
    var name: String = "",
    var gcn: String = "",
    var profileImageUrl: String = "",
    var bonusPoint: Int = 0,
    var minusPoint: Int = 0,
    var phrase: String = "",
)