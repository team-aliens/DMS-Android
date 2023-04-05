package team.aliens.dms_android.feature.auth.login

import team.aliens.dms_android.base.MviState
import team.aliens.local_domain.entity.notice.UserVisibleInformEntity

data class SignInState(
    val id: String,
    val password: String,
    var autoLogin: Boolean,
    var userVisibleInformEntity: UserVisibleInformEntity,
) : MviState {
    companion object {
        fun getDefaultInstance() = SignInState(
            id = "",
            password = "",
            autoLogin = false,
            userVisibleInformEntity = UserVisibleInformEntity(
                mealService = false,
                noticeService = false,
                pointService = false,
                studyRoomService = false,
                remainService = false,
            ),
        )
    }
}
