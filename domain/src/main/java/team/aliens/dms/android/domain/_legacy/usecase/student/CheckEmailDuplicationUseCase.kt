package team.aliens.dms.android.domain._legacy.usecase.student

import team.aliens.dms.android.domain.model.student.CheckEmailDuplicationInput
import team.aliens.dms.android.domain.repository.StudentRepository
import javax.inject.Inject

class CheckEmailDuplicationUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(
        checkEmailDuplicationInput: CheckEmailDuplicationInput,
    ) {
        studentRepository.checkEmailDuplication(
            input = checkEmailDuplicationInput,
        )
    }
}
