package team.aliens.dms.android.app.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import team.aliens.dms.android.data.point.model.PointType
import team.aliens.dms.android.feature.signup.model.SignUpData

@Serializable
data object OnboardingScreenNav : NavKey

@Serializable
data object SignInScreenNav : NavKey

@Serializable
data object HomeScreenNav : NavKey

@Serializable
data object MealScreenNav : NavKey

@Serializable
data object ApplicationScreenNav : NavKey

@Serializable
data object VoteScreenNav : NavKey

@Serializable
data object RemainScreenNav : NavKey

@Serializable
data object MyPageScreenNav : NavKey

@Serializable
data object SettingScreenNav : NavKey

@Serializable
data class PointHistoryScreenNav(val pointType: PointType) : NavKey

@Serializable
data object CheckPasswordScreenNav : NavKey

@Serializable
data class EditPasswordScreenNav(val currentPassword: String) : NavKey

@Serializable
data object SelectProfileScreenNav : NavKey

@Serializable
data class AdjustProfileScreenNav(val model: String) : NavKey

@Serializable
data object NotificationScreenNav : NavKey

@Serializable
data object NoticeDetailScreenNav : NavKey

@Serializable
data object FindIdScreenNav : NavKey

@Serializable
data object SignUpEnterSchoolVerificationCodeNav : NavKey

@Serializable
data class SignUpEnterSchoolVerificationQuestionNav(val signUpData: SignUpData) : NavKey

@Serializable
data class SignUpEnterEmailNav(val signUpData: SignUpData) : NavKey

@Serializable
data class SignUpEnterEmailVerificationCodeNav(val signUpData: SignUpData) : NavKey

@Serializable
data class SignUpEnterStudentNumberNav(val signUpData: SignUpData) : NavKey

@Serializable
data class SignUpSetIdNav(val signUpData: SignUpData) : NavKey

@Serializable
data class SignUpSetPasswordNav(val signUpData: SignUpData) : NavKey

@Serializable
data class SignUpTermsNav(val signUpData: SignUpData) : NavKey

@Serializable
data object SignUpCompleteNav : NavKey