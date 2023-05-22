package team.aliens.dms_android.feature.auth.signin

import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.MviViewModel
import team.aliens.dms_android.base.UiEvent
import team.aliens.dms_android.base.UiState
import team.aliens.domain.model.auth.SignInInput
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
            is SignInUiEvent.UpdateAutoSignInOption -> this.updateAutoSignInOption(
                newAutoSignInOption = event.newAutoSignInOption,
            )

            is SignInUiEvent.UpdateAccountId -> this.updateId(
                newId = event.newAccountId,
            )

            is SignInUiEvent.UpdatePassword -> this.updatePassword(
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
            }
        }
    }

    private fun trySignInOrElseErrorMessage() {
        if (idEntered.not()) // todo make as a function

        if (idEntered && passwordEntered)
            signIn()
    }

    private fun updateAutoSignInOption(
        newAutoSignInOption: Boolean,
    ) {
        setState(
            newState = uiState.value.copy(
                autoSignIn = newAutoSignInOption,
            ),
        )
    }

    private fun updateId(
        newId: String,
    ) {
        setState(
            newState = uiState.value.copy(
                accountId = newId,
            ),
        )
    }

    private fun updatePassword(
        newPassword: String,
    ) {
        setState(
            newState = uiState.value.copy(
                password = newPassword,
            ),
        )
    }

    private fun checkSignInAvailable() {

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

/*

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import team.aliens.dms_android.base.BaseViewModel1
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain.model._common.toModel
import team.aliens.domain.model.auth.SignInInput
import team.aliens.domain.model.student.Feature
import team.aliens.domain.usecase.auth.SignInUseCase
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
) : BaseViewModel1<SignInState, SignInEvent>() {

    override val initialState: SignInState
        get() = SignInState.getDefaultInstance()

    private val _signInViewEffect = MutableEventFlow<Event>()
    internal val signInViewEffect = _signInViewEffect.asEventFlow()

    private val _signInButtonState = MutableStateFlow(false)
    internal val signInButtonState = _signInButtonState.asStateFlow()

    private var idEntered: Boolean = false
        set(value) {
            field = value
            checkSignInAvailable()
        }

    private var passwordEntered = false
        set(value) {
            field = value
            checkSignInAvailable()
        }

    private fun checkSignInAvailable() {
        viewModelScope.launch(Dispatchers.Main) {
            _signInButtonState.emit(idEntered && passwordEntered)
        }
    }

    internal fun disableSignInButton() {
        runBlocking {
            _signInButtonState.emit(false)
        }
    }

    internal fun setId(
        id: String,
    ) {

        sendEvent(
            SignInEvent.InputId(id),
        )

        idEntered = id.isNotBlank()
    }

    internal fun setPassword(
        password: String,
    ) {

        sendEvent(
            SignInEvent.InputPassword(password),
        )

        passwordEntered = password.isNotBlank()
    }


    internal fun postSignIn() {
        viewModelScope.launch {

            val result = kotlin.runCatching {
                signInUseCase(
                    signInInput = getSignInInputFromCurrentState(),
                )
            }

            if (result.isSuccess) {
                emitSignInViewEvent(
                    Event.NavigateToHome(
                        features = result.getOrThrow().features.toModel() //fixme refactor
                    ),
                )
            } else {
                emitSignInViewEvent(
                    event = getEventFromThrowable(result.exceptionOrNull())
                )
            }
        }
    }

    private fun emitSignInViewEvent(
        event: Event,
    ) {
        viewModelScope.launch {
            _signInViewEffect.emit(event)
        }
    }

    override fun reduceEvent(
        oldState: SignInState,
        event: SignInEvent,
    ) {
        when (event) {
            is SignInEvent.InputId -> {
                setState(
                    oldState.copy(
                        id = event.id,
                    ),
                )
            }

            is SignInEvent.InputPassword -> {
                setState(
                    oldState.copy(
                        password = event.password,
                    ),
                )
            }

            else -> {
                *//* explicit blank *//*

            }
        }
    }

    private fun getSignInInputFromCurrentState(): SignInInput {
        return SignInInput(
            accountId = state.value.id,
            password = state.value.password,
            autoSignIn = state.value.autoLogin,
        )
    }

    sealed class Event {
        data class NavigateToHome(
            val features: Feature,
        ) : Event()

        object WrongRequest : Event()
        object NotCorrectPassword : Event()
        object UserNotFound : Event()
        object TooManyRequest : Event()
        object ServerException : Event()
        object NoInternetException : Event()
        object UnKnownException : Event()
    }
}

// todo 추후에 리팩토링 필요
private fun getEventFromThrowable(
    throwable: Throwable?,
): SignInViewModel.Event {
    return when (throwable) {
        else -> SignInViewModel.Event.UnKnownException
    }
}
*/
