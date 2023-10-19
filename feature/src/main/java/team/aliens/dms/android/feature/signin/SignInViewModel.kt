package team.aliens.dms.android.feature.signin

import dagger.hilt.android.lifecycle.HiltViewModel
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import javax.inject.Inject

@HiltViewModel
internal class SignInViewModel @Inject constructor(

) : BaseMviViewModel<SignInUiState, SignInIntent, SignInSideEffect>(
    initialState = SignInUiState.initial(),
) {

}

internal data class SignInUiState(
    val accountId: String,
    val password: String,
) : UiState() {
    companion object {
        fun initial() = SignInUiState(
            accountId = "",
            password = "",
        )
    }
}

internal sealed class SignInIntent : Intent()

internal sealed class SignInSideEffect : SideEffect()

/*

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import team.aliens.dms.android.feature._legacy.base.BaseMviViewModel
import team.aliens.dms.android.feature._legacy.base.MviIntent
import team.aliens.dms.android.feature._legacy.base.MviSideEffect
import team.aliens.dms.android.feature._legacy.base.MviState
import team.aliens.dms.android.domain._legacy.exception.AuthException
import team.aliens.dms.android.domain._legacy.exception.RemoteException
import team.aliens.dms.android.domain._legacy.model._common.toModel
import team.aliens.dms.android.domain.model.auth.SignInInput
import team.aliens.dms.android.domain.model.notification.RegisterDeviceNotificationTokenInput
import team.aliens.dms.android.domain.model.student.Features
import team.aliens.dms.android.domain.usecase.auth.SignInWithSavingTokensAndFeaturesUseCase
import team.aliens.dms.android.domain.usecase.notification.RegisterDeviceNotificationTokenUseCase
import javax.inject.Inject

@HiltViewModel
internal class SignInViewModel @Inject constructor(
    private val signInWithSavingTokensAndFeaturesUseCase: SignInWithSavingTokensAndFeaturesUseCase,
    private val registerDeviceNotificationTokenUseCase: RegisterDeviceNotificationTokenUseCase,
) : BaseMviViewModel<SignInIntent, SignInState, SignInSideEffect>(
    initialState = SignInState.initial(),
) {
    private val idEntered: Boolean
        get() = stateFlow.value.accountId.isNotBlank()
    private val passwordEntered: Boolean
        get() = stateFlow.value.password.isNotBlank()

    override fun processIntent(intent: SignInIntent) {
        when (intent) {
            is SignInIntent.SignIn -> this.signIn()
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

                signInWithSavingTokensAndFeaturesUseCase(
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
                // TODO: this@SignInViewModel.registerDeviceNotificationToken()
            }.onFailure {
                when (it) {
                    team.aliens.dms.android.domain._legacy.exception.RemoteException.BadRequest -> postSideEffect(SignInSideEffect.BadRequest)
                    team.aliens.dms.android.domain._legacy.exception.AuthException.UserNotFound -> postSideEffect(SignInSideEffect.IdNotFound)
                    team.aliens.dms.android.domain._legacy.exception.AuthException.PasswordMismatch -> postSideEffect(SignInSideEffect.PasswordMismatch)
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
        reduce(newState = currentState.copy(signInButtonEnabled = enabled))
    }
*/
/*
    private fun registerDeviceNotificationToken() {
        CoroutineScope(Dispatchers.IO).launch {
            kotlin.runCatching {
                val token = this@SignInViewModel.fetchDeviceNotificationToken()

                registerDeviceNotificationTokenUseCase(
                    registerDeviceNotificationTokenInput = RegisterDeviceNotificationTokenInput(
                        deviceToken = token,
                    ),
                )
            }.onFailure {
                postSideEffect(SignInSideEffect.DeviceTokenRegisteringFailure)
            }
        }
    }

    // todo 추후 분리 필요
    private fun fetchDeviceNotificationToken(): String {
        val task = FirebaseMessaging.getInstance().token
        val token = task.addOnCompleteListener {
            if (!it.isSuccessful) {
                postSideEffect(SignInSideEffect.DeviceTokenRegisteringFailure)
                return@addOnCompleteListener
            }
        }
        runBlocking { delay(1000L) }
        return token.result
    }*//*

}

internal sealed interface SignInIntent : MviIntent {
    class UpdateAccountId(val newAccountId: String) : SignInIntent

    class UpdatePassword(val newPassword: String) : SignInIntent

    class UpdateAutoSignInOption(val newAutoSignInOption: Boolean) : SignInIntent

    object SignIn : SignInIntent
}

internal data class SignInState(
    val accountId: String,
    val password: String,
    val autoSignIn: Boolean,
    val signInButtonEnabled: Boolean,
    val idError: Boolean,
    val passwordError: Boolean,
    val features: Features,
) : MviState {
    companion object {
        fun initial() = SignInState(
            accountId = "",
            password = "",
            autoSignIn = false,
            signInButtonEnabled = false,
            idError = false,
            passwordError = false,
            features = Features.falseInitialized(),
        )
    }
}

internal sealed class SignInSideEffect : MviSideEffect {
    class SignInSuccess(val features: Features) : SignInSideEffect()
    object BadRequest : SignInSideEffect()
    object IdNotFound : SignInSideEffect()
    object PasswordMismatch : SignInSideEffect()
    object DeviceTokenRegisteringFailure : SignInSideEffect()
}
*/
