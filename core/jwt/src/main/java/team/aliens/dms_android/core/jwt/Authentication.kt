package team.aliens.dms_android.core.jwt

data class Authentication(
    val accessToken: String,
    val accessTokenExpiration: String,
    val refreshToken: String,
    val refreshTokenExpiration: String,
    val features: Features,
) {
    data class Features(
        val mealService: Boolean,
        val noticeService: Boolean,
        val pointService: Boolean,
        val studyRoomService: Boolean,
        val remainsService: Boolean,
    )
}
