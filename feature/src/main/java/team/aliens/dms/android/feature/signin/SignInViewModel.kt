package team.aliens.dms.android.feature.signin

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.auth.exception.AuthException
import team.aliens.dms.android.data.auth.exception.BadRequestException
import team.aliens.dms.android.data.auth.exception.PasswordMismatchException
import team.aliens.dms.android.data.auth.exception.UserNotFoundException
import team.aliens.dms.android.data.auth.repository.AuthRepository
import team.aliens.dms.android.shared.exception.UnknownException
import javax.inject.Inject

@HiltViewModel
internal class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseMviViewModel<SignInUiState, SignInIntent, SignInSideEffect>(
    initialState = SignInUiState.initial(),
) {

    override fun processIntent(intent: SignInIntent) {
        when (intent) {
            is SignInIntent.UpdateId -> updateId(intent.id)
            is SignInIntent.UpdatePassword -> updatePassword(intent.password)
            is SignInIntent.UpdateAutoSignInOption -> updateAutoSignIn(intent.autoSignIn)
            SignInIntent.SignIn -> signIn()
        }
    }

    private fun updateId(accountId: String): Boolean {
        updateSignInButtonAvailable(checkSignInAvailable())
        return reduce(
            newState = stateFlow.value.copy(
                accountId = accountId,
                accountIdError = null,
            ),
        )
    }

    private fun updatePassword(password: String): Boolean {
        updateSignInButtonAvailable(checkSignInAvailable())
        return reduce(
            newState = stateFlow.value.copy(
                password = password,
                passwordError = null,
            ),
        )
    }

    private fun updateAutoSignIn(autoSignIn: Boolean): Boolean =
        reduce(newState = stateFlow.value.copy(autoSignIn = autoSignIn))

    private fun updateSignInButtonAvailable(signInButtonAvailable: Boolean): Boolean =
        reduce(newState = stateFlow.value.copy(signInButtonAvailable = signInButtonAvailable))

    private fun checkSignInAvailable(): Boolean {
        val uiState = stateFlow.value

        val idEntered = uiState.accountId.isNotBlank()
        val passwordEntered = uiState.password.isNotBlank()

        return idEntered && passwordEntered
    }

    private fun signIn() {
        updateSignInButtonAvailable(false)
        val uiState = stateFlow.value
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                authRepository.signIn(
                    accountId = uiState.accountId.trim(),
                    password = uiState.password.trim(),
                    autoSignIn = uiState.autoSignIn,
                )
            }.onSuccess {
                postSideEffect(SignInSideEffect.Success)
            }.onFailure { error ->
                when (error) {
                    is BadRequestException -> postSideEffect(SignInSideEffect.BadRequest)
                    is UserNotFoundException -> updateIdError(error)
                    is PasswordMismatchException -> updatePasswordError(error)
                    else -> throw UnknownException()
                }
            }.also {
                updateSignInButtonAvailable(true)
            }
        }
    }

    private fun updateIdError(exception: AuthException): Boolean =
        reduce(newState = stateFlow.value.copy(accountIdError = exception))

    private fun updatePasswordError(exception: AuthException): Boolean =
        reduce(newState = stateFlow.value.copy(passwordError = exception))
}

internal data class SignInUiState(
    val accountId: String,
    val password: String,
    val autoSignIn: Boolean,
    val signInButtonAvailable: Boolean,
    val accountIdError: AuthException?,
    val passwordError: AuthException?,
) : UiState() {
    companion object {
        fun initial() = SignInUiState(
            accountId = "",
            password = "",
            autoSignIn = false,
            signInButtonAvailable = false,
            accountIdError = null,
            passwordError = null,
        )
    }
}

internal sealed class SignInIntent : Intent() {

    class UpdateId(val id: String) : SignInIntent()

    class UpdatePassword(val password: String) : SignInIntent()

    class UpdateAutoSignInOption(val autoSignIn: Boolean) : SignInIntent()

    data object SignIn : SignInIntent()
}

internal sealed class SignInSideEffect : SideEffect() {

    data object Loading : SignInSideEffect()

    data object Failure : SignInSideEffect()

    data object Success : SignInSideEffect()

    object BadRequest : SignInSideEffect()
}
