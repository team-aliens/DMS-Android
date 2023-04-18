package team.aliens.domain.usecase.user

import team.aliens.domain._model.user.EditPasswordInput
import team.aliens.domain._repository.UserRepository
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
