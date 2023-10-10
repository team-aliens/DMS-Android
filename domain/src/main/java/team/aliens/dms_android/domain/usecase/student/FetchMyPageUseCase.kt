package team.aliens.dms_android.domain.usecase.student

import team.aliens.dms_android.domain.model.mypage.MyPage
import team.aliens.dms_android.domain.model.student.toModel
import team.aliens.dms_android.domain.repository.StudentRepository
import javax.inject.Inject

class FetchMyPageUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(): MyPage {
        return studentRepository.fetchMyPage().toModel()
    }
}
