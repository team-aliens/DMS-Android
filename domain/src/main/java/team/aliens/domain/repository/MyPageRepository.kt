package team.aliens.domain.repository

import team.aliens.domain.entity.mypage.MyPageEntity
import team.aliens.domain.entity.mypage.PointListEntity
import team.aliens.domain.enums.PointType

interface MyPageRepository {

    suspend fun fetchMyPage(): MyPageEntity

    suspend fun fetchPointList(pointType: PointType): PointListEntity
}
