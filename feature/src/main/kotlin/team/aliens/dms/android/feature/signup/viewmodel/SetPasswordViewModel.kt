package team.aliens.dms.android.feature.signup.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.feature.signup.model.SignUpData
import team.aliens.dms.android.shared.validator.checkIfPasswordValid
import javax.inject.Inject

@HiltViewModel
internal class SetPasswordViewModel @Inject constructor() :
    BaseStateViewModel<SetPasswordState, SetPasswordSideEffect>(SetPasswordState()) {

    private lateinit var currentSignUpData: SignUpData

    internal fun initialize(signUpData: SignUpData) {
        currentSignUpData = signUpData
    }

    internal fun setPassword(password: String) {
        setState {
            it.copy(
                password = password,
                showPasswordDescription = !checkIfPasswordValid(password),
            )
        }
        setButtonEnabled()
    }

    internal fun setPasswordCheck(passwordCheck: String) {
        setState {
            it.copy(
                passwordCheck = passwordCheck,
                showCheckPasswordDescription = it.password != passwordCheck,
            )
        }
        setButtonEnabled()
    }

    private fun setButtonEnabled() = setState {
        val hasBlank = it.password.isBlank() || it.passwordCheck.isBlank()
        val hasError = it.showPasswordDescription || it.showCheckPasswordDescription
        it.copy(buttonEnabled = !hasBlank && !hasError)
    }

    internal fun onNextClick() {
        sendEffect(
            SetPasswordSideEffect.MoveToTerms(
                signUpData = currentSignUpData.copy(password = uiState.value.password)
            )
        )
    }
}

internal data class SetPasswordState(
    val password: String = "",
    val passwordCheck: String = "",
    val showPasswordDescription: Boolean = false,
    val showCheckPasswordDescription: Boolean = false,
    val buttonEnabled: Boolean = false,
)

internal sealed interface SetPasswordSideEffect {
    data class MoveToTerms(val signUpData: SignUpData) : SetPasswordSideEffect
}
