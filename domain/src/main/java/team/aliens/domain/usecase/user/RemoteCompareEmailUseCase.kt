package team.aliens.domain.usecase.user

import team.aliens.domain.param.CompareEmailParam
import team.aliens.domain.repository.UserRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteCompareEmailUseCase @Inject constructor(
    private val userRepository: UserRepository,
) : UseCase<CompareEmailParam, Unit>() {
    override suspend fun execute(data: CompareEmailParam) {
        userRepository.compareEmail(data)
    }
}
