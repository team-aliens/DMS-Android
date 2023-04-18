package team.aliens.domain.usecase.auth

import team.aliens.domain._model.auth.CheckIdExistsOutput
import team.aliens.domain._repository.AuthRepository
import javax.inject.Inject

class CheckIdExistsUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        accountId: String,
    ): CheckIdExistsOutput {
        return authRepository.checkIdExists(
            accountId = accountId,
        )
    }
}
