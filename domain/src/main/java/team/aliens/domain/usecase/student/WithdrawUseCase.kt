package team.aliens.domain.usecase.student

import team.aliens.domain._repository.StudentRepository
import javax.inject.Inject

class WithdrawUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke() {
        studentRepository.withdraw()
    }
}
