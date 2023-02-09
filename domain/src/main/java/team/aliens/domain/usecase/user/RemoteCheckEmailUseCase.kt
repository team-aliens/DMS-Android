package team.aliens.domain.usecase.user

import team.aliens.domain.param.CheckEmailCodeParam
import team.aliens.domain.repository.UserRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteCheckEmailUseCase @Inject constructor(
    private val userRepository: UserRepository,
) : UseCase<CheckEmailCodeParam, Unit>() {
    override suspend fun execute(data: CheckEmailCodeParam) {
        userRepository.checkEmailCode(data)
    }
}
