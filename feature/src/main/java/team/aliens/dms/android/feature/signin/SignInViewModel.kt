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
import team.aliens.dms.android.data.auth.repository.AuthRepository
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
        return reduce(newState = stateFlow.value.copy(accountId = accountId))
    }

    private fun updatePassword(password: String): Boolean {
        updateSignInButtonAvailable(checkSignInAvailable())
        return reduce(newState = stateFlow.value.copy(password = password))
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
                /*when (error) {
                    is
                }*/

                error.printStackTrace()
                postSideEffect(SignInSideEffect.Failure)
            }.also {
                updateSignInButtonAvailable(true)
            }
        }
    }
}

internal data class SignInUiState(
    val accountId: String,
    val password: String,
    val autoSignIn: Boolean,
    val signInButtonAvailable: Boolean,
) : UiState() {
    companion object {
        fun initial() = SignInUiState(
            accountId = "",
            password = "",
            autoSignIn = false,
            signInButtonAvailable = false,
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

    class IdError(val error: AuthException) : SignInSideEffect()

    class PasswordError(val error: AuthException) : SignInSideEffect()
}
