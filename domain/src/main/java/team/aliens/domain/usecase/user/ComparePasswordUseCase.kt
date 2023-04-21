package team.aliens.domain.usecase.user

import team.aliens.domain._model.user.ComparePasswordInput
import team.aliens.domain._repository.UserRepository
import javax.inject.Inject

class ComparePasswordUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        comparePasswordInput: ComparePasswordInput,
    ) {
        userRepository.comparePassword(
            input = comparePasswordInput,
        )
    }
}
