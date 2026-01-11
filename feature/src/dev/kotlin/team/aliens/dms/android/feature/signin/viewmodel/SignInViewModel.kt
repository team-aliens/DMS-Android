package team.aliens.dms.android.feature.signin.viewmodel

import android.util.Log
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.auth.exception.BadRequestException
import team.aliens.dms.android.data.auth.repository.AuthRepository
import team.aliens.dms.android.data.notification.repository.NotificationRepository
import team.aliens.dms.android.shared.exception.network.InternalServerErrorException
import team.aliens.dms.android.shared.exception.network.NotFoundException
import team.aliens.dms.android.shared.exception.network.UnprocessableEntityException
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
                    Log.d("TEST", it.toString())
                    val errorMessage = when (it) { // TODO :: onFailure 자체에서 받아올 수 있게 구조 변경
                        is BadRequestException -> "잘못된 형식이에요"
                        is UnprocessableEntityException -> "비밀번호가 일치하지 않아요"
                        is NotFoundException -> "로그인 정보를 다시 확인해주세요"
                        is InternalServerErrorException -> "서버에 문제가 발생했어요"
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
