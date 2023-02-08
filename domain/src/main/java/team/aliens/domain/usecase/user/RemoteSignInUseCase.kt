package team.aliens.domain.usecase.user

import com.example.domain.param.LoginParam
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.UseCase
import javax.inject.Inject

class RemoteSignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
): UseCase<LoginParam, Unit>() {

    override suspend fun execute(data: LoginParam) {
        userRepository.userSignIn(data)
    }
}
