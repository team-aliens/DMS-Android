package team.aliens.domain.usecase.user

import team.aliens.domain.repository.UserRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class AutoSignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
) : UseCase<Unit, Unit>() {

    override suspend fun execute(data: Unit): Unit = userRepository.autoSignIn()
}