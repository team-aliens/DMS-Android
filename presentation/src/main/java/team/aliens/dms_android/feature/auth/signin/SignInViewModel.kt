package team.aliens.dms_android.feature.auth.signin

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.BaseMviViewModel
import team.aliens.dms_android.base.MviIntent
import team.aliens.dms_android.base.MviSideEffect
import team.aliens.dms_android.base.MviState
import team.aliens.domain.exception.AuthException
import team.aliens.domain.exception.RemoteException
import team.aliens.domain.model._common.toModel
import team.aliens.domain.model.auth.SignInInput
import team.aliens.domain.model.student.Feature
import team.aliens.domain.usecase.auth.SignInUseCase
import javax.inject.Inject

@HiltViewModel
internal class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
) : BaseMviViewModel<SignInIntent, SignInState, SignInSideEffect>(
    initialState = SignInState.initial(),
) {
    private val idEntered: Boolean
        get() = stateFlow.value.accountId.isNotBlank()
    private val passwordEntered: Boolean
        get() = stateFlow.value.password.isNotBlank()

    override fun processIntent(intent: SignInIntent) {
        when (intent) {
            SignInIntent.SignIn -> this.signIn()
            is SignInIntent.UpdateAutoSignInOption -> this.setAutoSignInOption(
                newAutoSignInOption = intent.newAutoSignInOption,
            )

            is SignInIntent.UpdateAccountId -> this.setId(
                newId = intent.newAccountId,
            )

            is SignInIntent.UpdatePassword -> this.setPassword(
                newPassword = intent.newPassword,
            )
        }
    }

    private fun signIn() {
        setSignInButtonState(false)

        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                val currentUserInformation = this@SignInViewModel.stateFlow.value

                signInUseCase(
                    signInInput = SignInInput(
                        accountId = currentUserInformation.accountId,
                        password = currentUserInformation.password,
                        autoSignIn = currentUserInformation.autoSignIn,
                    ),
                )
            }.onSuccess {
                postSideEffect(
                    sideEffect = SignInSideEffect.SignInSuccess(
                        features = it.features.toModel(),
                    ),
                )
            }.onFailure {
                when (it) {
                    RemoteException.BadRequest -> postSideEffect(SignInSideEffect.BadRequest)
                    AuthException.UserNotFound -> postSideEffect(SignInSideEffect.IdNotFound)
                    AuthException.PasswordMismatch -> postSideEffect(SignInSideEffect.PasswordMismatch)
                }
            }
        }
    }

    private fun setAutoSignInOption(
        newAutoSignInOption: Boolean,
    ) {
        reduce(
            newState = currentState.copy(
                autoSignIn = newAutoSignInOption,
            ),
        )
    }

    private fun setId(
        newId: String,
    ) {
        reduce(
            newState = currentState.copy(
                idError = false,
                accountId = newId,
            ),
        )

        setSignInButtonState()
    }

    private fun setPassword(
        newPassword: String,
    ) {
        reduce(
            newState = currentState.copy(
                passwordError = false,
                password = newPassword,
            ),
        )

        setSignInButtonState()
    }

    private fun setSignInButtonState(
        enabled: Boolean = idEntered && passwordEntered
    ) {
        reduce(
            newState = currentState.copy(
                signInButtonEnabled = enabled,
            ),
        )
    }
}

internal sealed interface SignInIntent : MviIntent {
    class UpdateAccountId(
        val newAccountId: String,
    ) : SignInIntent

    class UpdatePassword(
        val newPassword: String,
    ) : SignInIntent

    class UpdateAutoSignInOption(
        val newAutoSignInOption: Boolean,
    ) : SignInIntent

    object SignIn : SignInIntent
}

internal data class SignInState(
    val accountId: String,
    val password: String,
    val autoSignIn: Boolean,
    val signInButtonEnabled: Boolean,
    val idError: Boolean,
    val passwordError: Boolean,
    val features: Feature,
) : MviState {
    companion object {
        fun initial() = SignInState(
            accountId = "",
            password = "",
            autoSignIn = false,
            signInButtonEnabled = false,
            idError = false,
            passwordError = false,
            features = Feature.falseInitialized(),
        )
    }
}

internal sealed class SignInSideEffect : MviSideEffect {
    class SignInSuccess(val features: Feature) : SignInSideEffect()
    object BadRequest : SignInSideEffect()
    object IdNotFound : SignInSideEffect()
    object PasswordMismatch : SignInSideEffect()
}