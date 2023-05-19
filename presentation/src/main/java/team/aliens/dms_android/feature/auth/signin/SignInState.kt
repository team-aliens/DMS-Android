package team.aliens.dms_android.feature.auth.signin

import team.aliens.dms_android._base.MviState
import team.aliens.domain.model.student.Feature

data class SignInState(
    val id: String,
    val password: String,
    var autoLogin: Boolean,
    var userVisibleInformEntity: Feature,
) : MviState {
    companion object {
        fun getDefaultInstance() = SignInState(
            id = "",
            password = "",
            autoLogin = false,
            userVisibleInformEntity = Feature(
                mealService = false,
                noticeService = false,
                pointService = false,
                studyRoomService = false,
                remainsService = false,
            ),
        )
    }
}
