package team.aliens.dms.android.domain._legacy.usecase.student

import team.aliens.dms.android.domain.model.student.EditProfileInput
import team.aliens.dms.android.domain.repository.StudentRepository
import javax.inject.Inject

class EditProfileUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(
        editProfileInput: EditProfileInput,
    ) {
        studentRepository.editProfile(
            input = editProfileInput,
        )
    }
}
