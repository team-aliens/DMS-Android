package team.aliens.domain.usecase.user

import com.example.domain.param.CheckEmailCodeParam
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.UseCase
import javax.inject.Inject

class RemoteCheckEmailUseCase @Inject constructor(
    private val userRepository: UserRepository,
): UseCase<CheckEmailCodeParam, Unit>() {
    override suspend fun execute(data: CheckEmailCodeParam) {
        userRepository.checkEmailCode(data)
    }
}
