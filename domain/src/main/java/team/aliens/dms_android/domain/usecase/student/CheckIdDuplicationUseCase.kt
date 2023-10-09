package team.aliens.dms_android.domain.usecase.student

import team.aliens.domain.model.student.CheckIdDuplicationInput
import team.aliens.domain.repository.StudentRepository
import javax.inject.Inject

class CheckIdDuplicationUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(
        checkIdDuplicationInput: CheckIdDuplicationInput,
    ) {
        studentRepository.checkIdDuplication(
            input = checkIdDuplicationInput,
        )
    }
}
