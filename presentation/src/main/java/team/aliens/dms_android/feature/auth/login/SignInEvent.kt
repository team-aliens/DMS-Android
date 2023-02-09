package team.aliens.dms_android.feature.auth.login

import team.aliens.dms_android.base.MviEvent

sealed class SignInEvent : MviEvent {
    data class InputId(val id: String) : SignInEvent()
    data class InputPassword(val password: String) : SignInEvent()
}
