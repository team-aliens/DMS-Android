package team.aliens.domain._param.auth.signin

/**
 * @author junsuPark
 * A response returned when sign in succeed
 * [accessToken] an access token
 * [accessTokenExpiredAt] access token expiration date
 * [refreshToken] a refresh token
 * [refreshTokenExpiredAt] refresh token expiration date
 * [features] a data class contains all the features, user's school has set
 */
data class SignInResponse(
    val accessToken: String,
    val accessTokenExpiredAt: String,
    val refreshToken: String,
    val refreshTokenExpiredAt: String,
    val features: Features,
) {

    /**
     * @author junsuPark
     * A set of features, which user's school set
     * [mealService] feature of meal
     * [noticeService] feature of notice
     * [pointService] feature of point
     * [studyRoomService] feature of study room
     * [remainService] feature of remain
     */
    data class Features(
        val mealService: Boolean,
        val noticeService: Boolean,
        val pointService: Boolean,
        val studyRoomService: Boolean,
        val remainService: Boolean,
    )
}
