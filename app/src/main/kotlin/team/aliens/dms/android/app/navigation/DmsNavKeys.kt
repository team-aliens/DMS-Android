package team.aliens.dms.android.app.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import team.aliens.dms.android.data.point.model.PointType

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
data class ResetPasswordScreenNav(val currentPassword: String) : NavKey

@Serializable
data object SelectProfileScreenNav : NavKey

@Serializable
data class AdjustProfileScreenNav(val model: String) : NavKey

@Serializable
data object NotificationScreenNav : NavKey

@Serializable
data object NoticeDetailScreenNav : NavKey