package team.aliens.domain._param.auth.signin

/**
 * @author junsuPark
 * A response returned when sign in succeed
 * @property accessToken an access token
 * @property accessTokenExpiredAt access token expiration date
 * @property refreshToken a refresh token
 * @property refreshTokenExpiredAt refresh token expiration date
 * @property features a data class contains all the features, user's school has set
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
     * @property mealService feature of meal
     * @property noticeService feature of notice
     * @property pointService feature of point
     * @property studyRoomService feature of study room
     * @property remainService feature of remain
     */
    data class Features(
        val mealService: Boolean,
        val noticeService: Boolean,
        val pointService: Boolean,
        val studyRoomService: Boolean,
        val remainService: Boolean,
    )
}
