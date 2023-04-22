package team.aliens.dms_android.feature.mypage

import kotlinx.coroutines.flow.MutableStateFlow
import team.aliens.dms_android.base.MviState
import team.aliens.domain._model._common.Sex
import team.aliens.domain.entity.mypage.PointListEntity
import team.aliens.domain.enums.PointType

data class MyPageState(
    var type: PointType,
    var totalPoint: Int,
    var myPageEntity: MutableStateFlow<MyPageEntity> = MutableStateFlow(MyPageEntity()),
    var pointListEntity: PointListEntity,
) : MviState {
    companion object {
        fun getDefaultInstance() = MyPageState(
            type = PointType.ALL,
            totalPoint = 0,
            pointListEntity = PointListEntity(
                totalPoint = 0,
                pointValue = listOf(
                    PointListEntity.PointValue(
                        pointId = null,
                        date = "",
                        pointType = PointType.ALL,
                        name = "",
                        score = 0
                    ),
                ),
            ),
        )
    }
}

data class MyPageEntity(
    var schoolName: String = "",
    var name: String = "",
    var gcn: String = "",
    var profileImageUrl: String = "",
    var sex: Sex = Sex.ALL,
    var bonusPoint: Int = 0,
    var minusPoint: Int = 0,
    var phrase: String = "",
)
