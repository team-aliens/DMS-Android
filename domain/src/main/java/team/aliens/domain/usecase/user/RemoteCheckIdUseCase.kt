package team.aliens.domain.usecase.user

import team.aliens.domain.entity.user.CheckIdEntity
import team.aliens.domain.repository.UserRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteCheckIdUseCase @Inject constructor(
    private val userRepository: UserRepository,
) : UseCase<String, CheckIdEntity>() {
    override suspend fun execute(data: String): CheckIdEntity {
        return userRepository.checkId(data)
    }
}
