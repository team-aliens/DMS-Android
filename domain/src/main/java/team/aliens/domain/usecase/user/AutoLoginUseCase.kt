package team.aliens.domain.usecase.user

import com.example.domain.repository.UserRepository
import com.example.domain.usecase.UseCase
import javax.inject.Inject

class AutoLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<Unit, Unit>() {

    override suspend fun execute(data: Unit): Unit =
        userRepository.autoSignIn()
}