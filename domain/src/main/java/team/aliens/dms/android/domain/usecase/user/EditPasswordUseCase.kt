package team.aliens.dms.android.domain.usecase.user

import team.aliens.dms.android.domain.model.user.EditPasswordInput
import team.aliens.dms.android.domain.repository.UserRepository
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
