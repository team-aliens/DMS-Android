package team.aliens.domain.usecase.student

import team.aliens.domain.entity.mypage.PointListEntity
import team.aliens.domain.enums.PointType
import team.aliens.domain.repository.MyPageRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemotePointUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository,
) : UseCase<PointType, PointListEntity>() {
    override suspend fun execute(data: PointType): PointListEntity =
        myPageRepository.fetchPointList(data)
}
