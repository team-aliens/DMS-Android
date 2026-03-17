package team.aliens.dms.android.feature.signup.model

import kotlinx.serialization.Serializable

@Serializable
data class SignUpData(
    val schoolId: String = "",
    val schoolCode: String = "",
    val schoolAnswer: String = "",
    val email: String = "",
    val authCode: String = "",
    val grade: Int = 0,
    val classRoom: Int = 0,
    val number: Int = 0,
    val accountId: String = "",
    val password: String = "",
    val profileImageUrl: String? = null,
)
