package team.aliens.domain.usecase.student

import team.aliens.domain.entity.mypage.MyPageEntity
import team.aliens.domain.repository.MyPageRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteMyPageUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository,
) : UseCase<Unit, MyPageEntity>() {
    override suspend fun execute(data: Unit): MyPageEntity = myPageRepository.fetchMyPage()
}
