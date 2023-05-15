package team.aliens.dms_android.feature.auth.signin

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import team.aliens.dms_android.base.BaseViewModel
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain._model.auth.SignInInput
import team.aliens.domain.exception.BadRequestException
import team.aliens.domain.exception.NoInternetException
import team.aliens.domain.exception.NotFoundException
import team.aliens.domain.exception.ServerException
import team.aliens.domain.exception.TooManyRequestException
import team.aliens.domain.exception.UnauthorizedException
import team.aliens.domain.param.LoginParam
import team.aliens.domain.usecase.auth.SignInUseCase
import team.aliens.local_domain.entity.notice.UserVisibleInformEntity
import team.aliens.local_domain.usecase.uservisible.LocalUserVisibleInformUseCase
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val localUserVisibleInformUseCase: LocalUserVisibleInformUseCase,
) : BaseViewModel<SignInState, SignInEvent>() {

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
                        localUserVisibleInformUseCase.execute(Unit),
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
                /* explicit blank */
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
            val userVisibleInformEntity: UserVisibleInformEntity,
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

private fun getEventFromThrowable(
    throwable: Throwable?,
): SignInViewModel.Event {
    return when (throwable) {
        is BadRequestException -> SignInViewModel.Event.WrongRequest
        is UnauthorizedException -> SignInViewModel.Event.NotCorrectPassword
        is NotFoundException -> SignInViewModel.Event.UserNotFound
        is TooManyRequestException -> SignInViewModel.Event.TooManyRequest
        is NoInternetException -> SignInViewModel.Event.NoInternetException
        is ServerException -> SignInViewModel.Event.ServerException
        else -> SignInViewModel.Event.UnKnownException
    }
}
