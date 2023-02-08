package team.aliens.domain.usecase.user

import com.example.domain.repository.UserRepository
import com.example.domain.usecase.UseCase
import javax.inject.Inject

class RemoteCheckIdUseCase @Inject constructor(
    private val userRepository: UserRepository,
): UseCase<String, Unit>() {
    override suspend fun execute(data: String) {
        userRepository.checkId(data)
    }
}
