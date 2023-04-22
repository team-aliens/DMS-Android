package team.aliens.domain.usecase.auth

import team.aliens.domain._model.auth.CheckIdExistsInput
import team.aliens.domain._model.auth.CheckIdExistsOutput
import team.aliens.domain._repository.AuthRepository
import javax.inject.Inject

class CheckIdExistsUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        checkIdExistsInput: CheckIdExistsInput,
    ): CheckIdExistsOutput {
        return authRepository.checkIdExists(
            input = checkIdExistsInput,
        )
    }
}
