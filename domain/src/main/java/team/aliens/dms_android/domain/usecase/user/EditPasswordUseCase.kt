package team.aliens.dms_android.domain.usecase.user

import team.aliens.domain.model.user.EditPasswordInput
import team.aliens.domain.repository.UserRepository
import javax.inject.Inject

class EditPasswordUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        editPasswordInput: EditPasswordInput,
    ) {
        userRepository.editPassword(
            input = editPasswordInput,
        )
    }
}
