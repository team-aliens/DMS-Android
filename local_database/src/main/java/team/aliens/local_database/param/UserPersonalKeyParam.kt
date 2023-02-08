package team.aliens.local_database.param

import com.example.local_domain.entity.UserVisibleLocalEntity
import org.threeten.bp.LocalDateTime

data class UserPersonalKeyParam(
    val accessToken: String,
    val accessTokenExpiredAt: LocalDateTime,
    val refreshToken: String,
    val refreshTokenExpiredAt: LocalDateTime,
)

fun UserPersonalKeyParam.toDbEntity() =
    UserVisibleLocalEntity(
        accessToken = accessToken,
        accessTokenExpiredAt = accessTokenExpiredAt,
        refreshToken = refreshToken,
        refreshTokenExpiredAt = refreshTokenExpiredAt,
    )