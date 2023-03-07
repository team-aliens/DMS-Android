package team.aliens.domain._param.auth.signin

data class SignInResponse(
    val accessToken: String,
    val accessTokenExpiredAt: String,
    val refreshToken: String,
    val refreshTokenExpiredAt: String,
    val features: Features,
) {
    data class Features(
        val mealService: Boolean,
        val noticeService: Boolean,
        val pointService: Boolean,
        val studyRoomService: Boolean,
        val remainService: Boolean,
    )
}
