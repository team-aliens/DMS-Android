package team.aliens.domain.usecase.auth

import team.aliens.domain.repository.UserRepository
import javax.inject.Inject

// FIXME: 새로운 domain repository로 이전 필요
class AutoSignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke() {
        userRepository.autoSignIn()
    }
}
