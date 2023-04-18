package team.aliens.domain.usecase.user

import team.aliens.domain._repository.UserRepository
import javax.inject.Inject

class ComparePasswordUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        password: String,
    ) {
        userRepository.comparePassword(
            password = password,
        )
    }
}
