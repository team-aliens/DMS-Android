package team.aliens.domain.usecase.mypage

import com.example.domain.entity.mypage.MyPageEntity
import com.example.domain.repository.MyPageRepository
import com.example.domain.usecase.UseCase
import javax.inject.Inject

class RemoteMyPageUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository
): UseCase<Unit, MyPageEntity>() {
    override suspend fun execute(data: Unit): MyPageEntity =
        myPageRepository.fetchMyPage()
}
