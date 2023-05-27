package team.aliens.dms_android.feature.auth.signin

import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.MviViewModel
import team.aliens.dms_android.base.UiEvent
import team.aliens.dms_android.base.UiState
import team.aliens.domain.model._common.toModel
import team.aliens.domain.model.auth.SignInInput
import team.aliens.domain.model.student.Feature
import team.aliens.domain.usecase.auth.SignInUseCase
import javax.inject.Inject

@HiltViewModel
internal class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
) : MviViewModel<SignInUiState, SignInUiEvent>(
    initialState = SignInUiState.initial(),
) {
    private val idEntered: Boolean
        get() = uiState.value.accountId.isNotBlank()
    private val passwordEntered: Boolean
        get() = uiState.value.password.isNotBlank()

    override fun updateState(event: SignInUiEvent) {
        when (event) {
            SignInUiEvent.SignIn -> this.signIn()
            is SignInUiEvent.UpdateAutoSignInOption -> this.setAutoSignInOption(
                newAutoSignInOption = event.newAutoSignInOption,
            )

            is SignInUiEvent.UpdateAccountId -> this.setId(
                newId = event.newAccountId,
            )

            is SignInUiEvent.UpdatePassword -> this.setPassword(
                newPassword = event.newPassword,
            )
        }
    }

    private fun signIn() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                val currentUserInformation = this@SignInViewModel.uiState.value

                signInUseCase(
                    signInInput = SignInInput(
                        accountId = currentUserInformation.accountId,
                        password = currentUserInformation.password,
                        autoSignIn = currentUserInformation.autoSignIn,
                    ),
                )
            }.onSuccess {
                this@SignInViewModel.setState(
                    newState = uiState.value.copy(
                        signInSuccess = true,
                        features = it.features.toModel(),
                    ),
                )
            }.onFailure {
                setState(
                    newState = uiState.value.copy(
                        error = it,
                    ),
                )
            }
        }
    }

    private fun setAutoSignInOption(
        newAutoSignInOption: Boolean,
    ) {
        setState(
            newState = uiState.value.copy(
                autoSignIn = newAutoSignInOption,
            ),
        )
    }

    private fun setId(
        newId: String,
    ) {
        setState(
            newState = uiState.value.copy(
                accountId = newId,
            ),
        )

        setSignInButtonState()
    }

    private fun setPassword(
        newPassword: String,
    ) {
        setState(
            newState = uiState.value.copy(
                password = newPassword,
            ),
        )

        setSignInButtonState()
    }

    // 그렇게 좋은 코드는 아닌 듯
    private fun setSignInButtonState() {
        setState(
            newState = uiState.value.copy(
                signInButtonEnabled = idEntered && passwordEntered,
            ),
        )
    }
}

@Stable
internal data class SignInUiState(
    val signInSuccess: Boolean,
    val accountId: String,
    val password: String,
    val autoSignIn: Boolean,
    val signInButtonEnabled: Boolean,
    val idError: Boolean,
    val passwordError: Boolean,
    val features: Feature,
    val error: Throwable?, // todo 상의 필요
) : UiState {
    companion object {
        fun initial() = SignInUiState(
            signInSuccess = false,
            accountId = "",
            password = "",
            autoSignIn = false,
            signInButtonEnabled = false,
            idError = false,
            passwordError = false,
            features = Feature.falseInitialized(),
            error = null,
        )
    }
}

internal sealed interface SignInUiEvent : UiEvent {
    class UpdateAccountId(
        val newAccountId: String,
    ) : SignInUiEvent

    class UpdatePassword(
        val newPassword: String,
    ) : SignInUiEvent

    class UpdateAutoSignInOption(
        val newAutoSignInOption: Boolean,
    ) : SignInUiEvent

    object SignIn : SignInUiEvent
}
