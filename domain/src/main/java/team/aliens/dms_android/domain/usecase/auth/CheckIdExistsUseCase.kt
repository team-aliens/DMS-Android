package team.aliens.dms_android.domain.usecase.auth

import team.aliens.domain.model.auth.CheckIdExistsInput
import team.aliens.domain.model.auth.CheckIdExistsOutput
import team.aliens.domain.repository.AuthRepository
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
