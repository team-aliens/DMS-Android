package team.aliens.dms.android.domain.usecase.auth

import team.aliens.dms.android.domain.model._common.AuthenticationOutput
import team.aliens.dms.android.domain.model._common.toModel
import team.aliens.dms.android.domain.model.auth.Token
import team.aliens.dms.android.domain.repository.AuthRepository
import team.aliens.dms.android.domain.repository.SchoolRepository
import javax.inject.Inject

class ReissueTokenWithSavingTokensAndFeaturesUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val schoolRepository: SchoolRepository,
) {
    suspend operator fun invoke(): AuthenticationOutput {
        return authRepository.reissueToken().also {
            val token = Token(
                accessToken = it.accessToken,
                accessTokenExpiredAt = it.accessTokenExpiredAt,
                refreshToken = it.refreshToken,
                refreshTokenExpiredAt = it.refreshTokenExpiredAt,
            )
            val features = it.features.toModel()

            authRepository.saveToken(token)
            schoolRepository.saveFeatures(features)
        }
    }
}
