package team.aliens.dms_android.domain.usecase.auth

import team.aliens.dms_android.domain.model._common.AuthenticationOutput
import team.aliens.dms_android.domain.model._common.toModel
import team.aliens.domain.model.auth.Token
import team.aliens.domain.repository.AuthRepository
import team.aliens.domain.repository.SchoolRepository
import javax.inject.Inject

class ReissueTokenWithSavingTokensAndFeaturesUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val schoolRepository: SchoolRepository,
) {
    suspend operator fun invoke(): _root_ide_package_.team.aliens.dms_android.domain.model._common.AuthenticationOutput {
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
