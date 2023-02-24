package team.aliens.domain.usecase.user

import team.aliens.domain.repository.UserRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class ReissueTokenUseCase @Inject constructor(
    private val userRepository: UserRepository,
) : UseCase<String, Unit>() {
    override suspend fun execute(data: String) {
        userRepository.reissueToken(data)
    }
}
