package team.aliens.domain.usecase.student

import team.aliens.domain.model.mypage.MyPage
import team.aliens.domain.model.student.toModel
import team.aliens.domain.repository.StudentRepository
import javax.inject.Inject

class FetchMyPageUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(): MyPage {
        return studentRepository.fetchMyPage().toModel()
    }
}
