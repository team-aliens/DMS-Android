package team.aliens.dms.android.data.auth.mapper

import team.aliens.dms.android.core.jwt.AccessToken
import team.aliens.dms.android.core.jwt.RefreshToken
import team.aliens.dms.android.core.jwt.Tokens
import team.aliens.dms.android.core.school.Features
import team.aliens.dms.android.network.auth.model.SignInResponse
import team.aliens.dms.android.shared.date.toLocalDateTime

internal fun SignInResponse.extractTokens(): Tokens = Tokens(
    accessToken = AccessToken(
        value = this.accessToken,
        expiration = this.accessTokenExpiration.toLocalDateTime(),
    ),
    refreshToken = RefreshToken(
        value = this.refreshToken,
        expiration = this.refreshTokenExpiration.toLocalDateTime(),
    ),
)

internal fun SignInResponse.extractFeatures(): Features = Features(
    mealService = this.features.mealService,
    noticeService = this.features.noticeService,
    pointService = this.features.pointService,
    studyRoomService = this.features.studyRoomService,
    remainsService = this.features.remainsService,
)
