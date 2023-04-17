package team.aliens.domain.usecase.student

import team.aliens.domain._repository.StudentRepository
import javax.inject.Inject

class CheckEmailDuplicationUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(
        email: String,
    ) {
        studentRepository.checkEmailDuplication(
            email = email,
        )
    }
}
