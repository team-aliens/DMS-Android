package team.aliens.domain.usecase.user

import team.aliens.domain.param.LoginParam
import team.aliens.domain.repository.UserRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteSignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
) : UseCase<LoginParam, Unit>() {

    override suspend fun execute(data: LoginParam) {
        userRepository.userSignIn(data)
    }
}
