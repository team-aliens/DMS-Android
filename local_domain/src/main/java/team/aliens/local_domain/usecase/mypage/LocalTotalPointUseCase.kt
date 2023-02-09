package team.aliens.local_domain.usecase.mypage

import team.aliens.local_domain.repository.mypage.LocalMyPageRepository
import team.aliens.local_domain.usecase.UseCase
import javax.inject.Inject

class LocalTotalPointUseCase @Inject constructor(
    private val localMyPageRepository: LocalMyPageRepository,
) : UseCase<Unit, Int>() {

    override suspend fun execute(data: Unit): Int = localMyPageRepository.fetchTotalPoint()
}
