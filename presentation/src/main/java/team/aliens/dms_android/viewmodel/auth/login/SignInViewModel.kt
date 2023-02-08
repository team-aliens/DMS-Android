package team.aliens.dms_android.viewmodel.auth.login

import androidx.lifecycle.viewModelScope
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.feature.auth.login.SignInEvent
import com.example.dms_android.feature.auth.login.SignInState
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import com.example.domain.exception.BadRequestException
import com.example.domain.exception.NoInternetException
import com.example.domain.exception.NotFoundException
import com.example.domain.exception.ServerException
import com.example.domain.exception.TooManyRequestException
import com.example.domain.exception.UnauthorizedException
import com.example.domain.param.LoginParam
import com.example.domain.usecase.user.RemoteSignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val remoteSignInUseCase: RemoteSignInUseCase,
) : BaseViewModel<SignInState, SignInEvent>() {

    private var parameter =
        LoginParam(id = state.value.id, password = state.value.password, autoLogin = state.value.autoLogin)

    fun setId(id: String) {
        sendEvent(SignInEvent.InputId(id))
    }

    fun setPassword(password: String) {
        sendEvent(SignInEvent.InputPassword(password))
    }

    override val initialState: SignInState
        get() = SignInState.initial()

    private val _signInViewEffect = MutableEventFlow<Event>()
    var signInViewEffect = _signInViewEffect.asEventFlow()

    fun postSignIn() {
        parameter =
            LoginParam(id = state.value.id, password = state.value.password, autoLogin = state.value.autoLogin)
        viewModelScope.launch {
            kotlin.runCatching {
                remoteSignInUseCase.execute(parameter)
            }.onSuccess {
                event(Event.NavigateToHome)
            }.onFailure {
                when (it) {
                    is BadRequestException -> event(Event.WrongRequest)
                    is UnauthorizedException -> event(Event.NotCorrectPassword)
                    is NotFoundException -> event(Event.UserNotFound)
                    is TooManyRequestException -> event(Event.TooManyRequest)
                    is NoInternetException -> event(Event.NoInternetException)
                    is ServerException -> event(Event.ServerException)
                    else -> event(Event.UnKnownException)
                }
            }
        }
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _signInViewEffect.emit(event)
            signInViewEffect = _signInViewEffect.asEventFlow()
        }
    }

    override fun reduceEvent(oldState: SignInState, event: SignInEvent) {
        when (event) {
            is SignInEvent.InputId -> {
                setState(oldState.copy(id = event.id))
            }
            is SignInEvent.InputPassword -> {
                setState(oldState.copy(password = event.password))
            }
            else -> {}
        }
    }

    sealed class Event() {
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
