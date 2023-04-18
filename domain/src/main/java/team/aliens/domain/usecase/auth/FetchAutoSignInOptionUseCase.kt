package team.aliens.domain.usecase.auth

import team.aliens.domain.repository.UserRepository
import javax.inject.Inject

// FIXME: 새로운 domain repository로 이전 필요
class FetchAutoSignInOptionUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Boolean {
        return userRepository.fetchAutoSignInOption()
    }
}
