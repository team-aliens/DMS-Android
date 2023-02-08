package team.aliens.domain.repository

import com.example.domain.entity.mypage.MyPageEntity
import com.example.domain.entity.mypage.PointListEntity
import com.example.domain.enums.PointType

interface MyPageRepository {

    suspend fun fetchMyPage(): MyPageEntity

    suspend fun fetchPointList(pointType: PointType): PointListEntity
}
