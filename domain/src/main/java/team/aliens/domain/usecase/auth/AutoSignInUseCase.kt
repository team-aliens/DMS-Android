package team.aliens.domain.usecase.auth

import team.aliens.domain.repository.UserRepository
import javax.inject.Inject

class AutoSignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke() {
        userRepository.autoSignIn()
    }
}
