package team.aliens.dms.android.domain._legacy.usecase.student

import team.aliens.dms.android.domain.model.mypage.MyPage
import team.aliens.dms.android.domain.model.student.toModel
import team.aliens.dms.android.domain.repository.StudentRepository
import javax.inject.Inject

class FetchMyPageUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(): MyPage {
        return studentRepository.fetchMyPage().toModel()
    }
}