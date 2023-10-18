package team.aliens.dms.android.domain._legacy.usecase.auth

import team.aliens.dms.android.domain.model.auth.CheckIdExistsInput
import team.aliens.dms.android.domain.model.auth.CheckIdExistsOutput
import team.aliens.dms.android.domain.repository.AuthRepository
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
