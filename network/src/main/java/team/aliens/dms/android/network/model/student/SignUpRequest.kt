package team.aliens.dms.android.network.model.student

import com.google.gson.annotations.SerializedName
import team.aliens.dms_android.domain.model.student.SignUpInput

data class SignUpRequest(
    @SerializedName("school_code") val schoolVerificationCode: String,
    @SerializedName("school_answer") val schoolVerificationAnswer: String,
    @SerializedName("email") val email: String,
    @SerializedName("auth_code") val emailVerificationCode: String,
    @SerializedName("grade") val grade: Int,
    @SerializedName("class_room") val classRoom: Int,
    @SerializedName("number") val number: Int,
    @SerializedName("account_id") val accountId: String,
    @SerializedName("password") val password: String,
    @SerializedName("profile_image_url") val profileImageUrl: String?,
)

internal fun SignUpInput.toData(): SignUpRequest {
    return SignUpRequest(
        schoolVerificationCode = this.schoolVerificationCode,
        schoolVerificationAnswer = this.schoolVerificationAnswer,
        email = this.email,
        emailVerificationCode = this.emailVerificationCode,
        grade = this.grade,
        classRoom = this.classRoom,
        number = this.number,
        accountId = this.accountId,
        password = this.password,
        profileImageUrl = this.profileImageUrl,
    )
}
