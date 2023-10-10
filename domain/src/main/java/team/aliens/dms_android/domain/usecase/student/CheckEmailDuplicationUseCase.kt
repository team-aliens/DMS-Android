package team.aliens.dms_android.domain.usecase.student

import team.aliens.dms_android.domain.model.student.CheckEmailDuplicationInput
import team.aliens.dms_android.domain.repository.StudentRepository
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
