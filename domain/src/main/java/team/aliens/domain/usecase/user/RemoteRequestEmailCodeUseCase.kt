package team.aliens.domain.usecase.user

import team.aliens.domain.param.RequestEmailCodeParam
import team.aliens.domain.repository.UserRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteRequestEmailCodeUseCase @Inject constructor(
    private val userRepository: UserRepository,
) : UseCase<RequestEmailCodeParam, Unit>() {
    override suspend fun execute(data: RequestEmailCodeParam) {
        userRepository.requestEmailCode(data)
    }
}
