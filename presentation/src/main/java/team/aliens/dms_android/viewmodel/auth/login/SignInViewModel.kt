package team.aliens.dms_android.viewmodel.auth.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.BaseViewModel
import team.aliens.dms_android.feature.auth.login.SignInEvent
import team.aliens.dms_android.feature.auth.login.SignInState
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain.exception.*
import team.aliens.domain.param.LoginParam
import team.aliens.domain.usecase.user.RemoteSignInUseCase
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val remoteSignInUseCase: RemoteSignInUseCase,
) : BaseViewModel<SignInState, SignInEvent>() {

    override val initialState: SignInState
        get() = SignInState.getDefaultInstance()

    private val _signInViewEffect = MutableEventFlow<Event>()
    internal val signInViewEffect = _signInViewEffect.asEventFlow()

    internal fun setId(id: String) {
        sendEvent(SignInEvent.InputId(id))
    }

    internal fun setPassword(password: String) {
        sendEvent(SignInEvent.InputPassword(password))
    }

    internal fun postSignIn() {
        viewModelScope.launch {

            val result = kotlin.runCatching {
                remoteSignInUseCase.execute(
                    getLoginParamFromCurrentState(),
                )
            }

            emitSignInViewEvent(
                if (result.isSuccess) {
                    Event.NavigateToHome
                } else {
                    getEventFromThrowable(
                        result.exceptionOrNull(),
                    )
                },
            )
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

    private fun getLoginParamFromCurrentState(): LoginParam {
        return LoginParam(
            id = state.value.id,
            password = state.value.password,
            autoLogin = state.value.autoLogin,
        )
    }

    sealed class Event {
        object NavigateToHome : Event()
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
