package team.aliens.domain.model._common

import team.aliens.domain.model.student.Feature

/**
 * A response returned when authentication succeed
 * @property accessToken an access token
 * @property accessTokenExpiredAt access token expiration date
 * @property refreshToken a refresh token
 * @property refreshTokenExpiredAt refresh token expiration date
 * @property features a data class contains all the features, user's school has set
 */
data class AuthenticationOutput(
    val accessToken: String,
    val accessTokenExpiredAt: String,
    val refreshToken: String,
    val refreshTokenExpiredAt: String,
    val features: Features,
) {

    /**
     * A set of features, which user's school set
     * @property mealService feature of meal
     * @property noticeService feature of notice
     * @property pointService feature of point
     * @property studyRoomService feature of study room
     * @property remainsService feature of remain
     */
    data class Features(
        val mealService: Boolean,
        val noticeService: Boolean,
        val pointService: Boolean,
        val studyRoomService: Boolean,
        val remainsService: Boolean,
    )
}

fun AuthenticationOutput.Features.toModel(): Feature {
    return Feature(
        mealService = this.mealService,
        noticeService = this.noticeService,
        pointService = this.pointService,
        studyRoomService = this.studyRoomService,
        remainsService = this.remainsService,
    )
}
