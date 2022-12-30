package com.example.dms_android.feature.mypage

import com.example.dms_android.base.MviState
import com.example.domain.entity.mypage.PointListEntity
import com.example.domain.enums.PointType

data class MyPageState(
    var type: PointType,
    var totalPoint: Int,
    val myPageEntity: MyPageEntity = MyPageEntity(),
    var pointListEntity: PointListEntity
) : MviState {
    companion object {
        fun initial() =
            MyPageState(
                type = PointType.ALL,
                totalPoint = 0,
                pointListEntity =
                PointListEntity(
                    totalPoint = 0,
                    pointValue = listOf(
                        PointListEntity.PointValue(
                            pointId = "",
                            date = "",
                            pointType = PointType.ALL,
                            name = "",
                            score = 0
                        )
                    )
                )
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