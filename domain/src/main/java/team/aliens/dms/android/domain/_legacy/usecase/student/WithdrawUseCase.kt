package team.aliens.dms.android.domain._legacy.usecase.student

import team.aliens.dms.android.domain.repository.StudentRepository
import javax.inject.Inject

class WithdrawUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke() {
        studentRepository.withdraw()
    }
}
