package team.aliens.local_domain.usecase.mypage

import team.aliens.local_domain.param.MyPageParam
import team.aliens.local_domain.repository.mypage.LocalMyPageRepository
import team.aliens.local_domain.usecase.UseCase
import javax.inject.Inject

class LocalMyPageUseCase @Inject constructor(
    private val localMyPageRepository: LocalMyPageRepository,
) : UseCase<Unit, MyPageParam>() {

    override suspend fun execute(data: Unit): MyPageParam = localMyPageRepository.fetchMyPage()
}
