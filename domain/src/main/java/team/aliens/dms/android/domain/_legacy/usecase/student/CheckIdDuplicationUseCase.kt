package team.aliens.dms.android.domain._legacy.usecase.student

import team.aliens.dms.android.domain.model.student.CheckIdDuplicationInput
import team.aliens.dms.android.domain.repository.StudentRepository
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
