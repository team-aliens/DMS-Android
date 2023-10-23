package team.aliens.dms.android.data.auth.mapper

import team.aliens.dms.android.core.jwt.Tokens
import team.aliens.dms.android.core.school.Features
import team.aliens.dms.android.network.auth.model.SignInResponse
import team.aliens.dms.android.shared.date.toLocalDateTime

fun SignInResponse.extractTokens(): Tokens = Tokens(
    accessToken = this.accessToken,
    accessTokenExpiration = this.accessTokenExpiration.toLocalDateTime(),
    refreshToken = this.refreshToken,
    refreshTokenExpiration = this.refreshTokenExpiration.toLocalDateTime(),
)

fun SignInResponse.extractFeatures(): Features = Features(
    mealService = this.features.mealService,
    noticeService = this.features.noticeService,
    pointService = this.features.pointService,
    studyRoomService = this.features.studyRoomService,
    remainsService = this.features.remainsService,
)
