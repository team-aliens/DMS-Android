package team.aliens.dms_android.feature.auth.signin

import team.aliens.dms_android._base.MviEvent

sealed class SignInEvent : MviEvent {
    data class InputId(val id: String) : SignInEvent()
    data class InputPassword(val password: String) : SignInEvent()
}
