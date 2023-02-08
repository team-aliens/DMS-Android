package team.aliens.local_database.param.mypage

import com.example.local_domain.param.MyPageParam

data class MyPageLocalParam(
    val schoolName: String,
    val name: String,
    val gcn: String,
    val profileImageUrl: String,
    val bonusPoint: Int,
    val minusPoint: Int,
    val phrase: String,
)

fun MyPageLocalParam.toParam() =
    MyPageParam(
        schoolName = schoolName,
        name = name,
        gcn = gcn,
        profileImageUrl = profileImageUrl,
        bonusPoint = bonusPoint,
        minusPoint = minusPoint,
        phrase = phrase,
    )
