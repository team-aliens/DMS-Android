package team.aliens.dms.android.feature.signin.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.auth.repository.AuthRepository
import team.aliens.dms.android.data.notification.repository.NotificationRepository
import javax.inject.Inject


@HiltViewModel
internal class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val notificationRepository: NotificationRepository,
) : BaseStateViewModel<SignInState, SignInSideEffect>(SignInState.initial()) {

    init {
        getDeviceToken()
    }

    internal fun setAccountId(accountId: String) {
        setState {
            it.copy(
                accountId = accountId,
                showAccountIdErrorDescription = false,
            )
        }
        setButtonEnabled()
    }

    internal fun setPassword(password: String) {
        setState {
            it.copy(
                password = password,
                showPasswordErrorDescription = false,
            )
        }
        setButtonEnabled()
    }

    private fun setButtonEnabled() = setState {
        with(uiState.value) {
            val isSignInValueNotBlank = accountId.isNotBlank() && password.isNotBlank()
            val hasNoError = !showAccountIdErrorDescription && !showPasswordErrorDescription
            copy(buttonEnabled = isSignInValueNotBlank && hasNoError)
        }
    }

    private fun getDeviceToken() {
        viewModelScope.launch(Dispatchers.IO) {
            notificationRepository.getDeviceToken().onSuccess { deviceToken ->
                setState { it.copy(deviceToken = deviceToken) }
            }
        }
    }

    internal fun signIn() {
        viewModelScope.launch {
            with(uiState.value) {
                setState { this.copy(isLoading = true, buttonEnabled = false) }
                authRepository.signIn(
                    accountId = this.accountId,
                    password = this.password,
                    deviceToken = deviceToken,
                ).onSuccess {
                    setState { this.copy(isLoading = false, buttonEnabled = true) }
                    sendEffect(SignInSideEffect.NavigateToMain)
                }.onFailure {
                    setState { this.copy(isLoading = false, buttonEnabled = true) }
                    val errorMessage = when (it.message?.drop(5)?.trim()) { // TODO :: 더 좋은 방법 고려
                        "400" -> "잘못된 비밀번호 형식이에요"
                        "422" -> "비밀번호가 일치하지 않아요"
                        "404" -> "존재하지 않는 학생입니다"
                        "500" -> "서버에 문제가 발생했어요"
                        else -> "일시적인 오류로 로그인을 할 수 없어요"
                    }
                    sendEffect(SignInSideEffect.ShowSnackBar(DmsSnackBarType.ERROR, errorMessage))
                }
            }
        }
    }
}

internal data class SignInState(
    val accountId: String,
    val password: String,
    val buttonEnabled: Boolean,
    val showAccountIdErrorDescription: Boolean,
    val showPasswordErrorDescription: Boolean,
    val isLoading: Boolean,
    val deviceToken: String,
) {
    companion object {
        fun initial() = SignInState(
            accountId = "",
            password = "",
            buttonEnabled = false,
            showAccountIdErrorDescription = false,
            showPasswordErrorDescription = false,
            isLoading = false,
            deviceToken = "",
        )
    }
}

internal sealed interface SignInSideEffect {
    data object NavigateToMain : SignInSideEffect
    data class ShowSnackBar(
        val snackBarType: DmsSnackBarType,
        val message: String,
    ) : SignInSideEffect
}
