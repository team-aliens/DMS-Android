package team.aliens.domain.usecase.user

import android.net.wifi.hotspot2.pps.Credential.UserCredential
import team.aliens.domain.repository.UserRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class ComparePasswordUseCase @Inject constructor(
    private val userRepository: UserRepository,
) : UseCase<String, Unit>() {
    override suspend fun execute(data: String) {
        userRepository.comparePassword(
            password = data,
        )
    }
}