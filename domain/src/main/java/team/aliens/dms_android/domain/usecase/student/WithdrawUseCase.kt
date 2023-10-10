package team.aliens.dms_android.domain.usecase.student

import team.aliens.dms_android.domain.repository.StudentRepository
import javax.inject.Inject

class WithdrawUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke() {
        studentRepository.withdraw()
    }
}
