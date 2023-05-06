package team.aliens.dms_android.feature.mypage

import kotlinx.coroutines.flow.MutableStateFlow
import team.aliens.dms_android.base.MviState
import team.aliens.domain._model._common.PointType
import team.aliens.domain._model._common.Sex
import team.aliens.domain._model.point.FetchPointsOutput

data class MyPageState(
    var type: MutableStateFlow<PointType>,
    var totalPoint: Int,
    var myPageEntity: MutableStateFlow<MyPageEntity> = MutableStateFlow(MyPageEntity()),
    var fetchPointsOutput: FetchPointsOutput,
) : MviState {
    companion object {
        fun getDefaultInstance() = MyPageState(
            type = MutableStateFlow(PointType.ALL),
            totalPoint = 0,
            fetchPointsOutput = FetchPointsOutput(
                totalPoint = 0,
                points = emptyList(),
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
