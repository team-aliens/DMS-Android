package team.aliens.domain.usecase.mypage

import com.example.domain.entity.mypage.PointListEntity
import com.example.domain.enums.PointType
import com.example.domain.repository.MyPageRepository
import com.example.domain.usecase.UseCase
import javax.inject.Inject

class RemotePointUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository
): UseCase<PointType, PointListEntity>() {
    override suspend fun execute(data: PointType): PointListEntity =
        myPageRepository.fetchPointList(data)
}
