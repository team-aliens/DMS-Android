package team.aliens.local_domain.usecase.mypage

import team.aliens.local_domain.entity.mypage.PointListValueEntity
import team.aliens.local_domain.repository.mypage.LocalMyPageRepository
import team.aliens.local_domain.usecase.UseCase
import javax.inject.Inject

class LocalPointListUseCase @Inject constructor(
    private val localMyPageRepository: LocalMyPageRepository,
) : UseCase<String, List<PointListValueEntity>>() {

    override suspend fun execute(data: String): List<PointListValueEntity> =
        localMyPageRepository.fetchPointList(data)
}
