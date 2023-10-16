package team.aliens.dms.android.domain.usecase.auth

import team.aliens.dms.android.domain.model._common.AuthenticationOutput
import team.aliens.dms.android.domain.model.auth.SignInInput
import team.aliens.dms.android.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        signInInput: SignInInput,
    ): AuthenticationOutput {
        return authRepository.signIn(
            input = signInInput,
        )
    }
}