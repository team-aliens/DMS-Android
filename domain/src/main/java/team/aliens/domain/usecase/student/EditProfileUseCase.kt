package team.aliens.domain.usecase.student

import team.aliens.domain._model.student.EditProfileInput
import team.aliens.domain._repository.StudentRepository
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
