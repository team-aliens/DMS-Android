package team.aliens.dms_android.feature.splash

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.BaseViewModel
import team.aliens.dms_android.base.MviEvent
import team.aliens.dms_android.base.MviState
import team.aliens.dms_android.feature.navigator.NavigationRoute
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain.usecase.user.AutoSignInUseCase
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val autoSignInUseCase: AutoSignInUseCase,
) : BaseViewModel<SplashState, SplashEvent>() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    fun autoLogin() = viewModelScope.launch {
        kotlin.runCatching {
            autoSignInUseCase.execute(Unit)
        }.onSuccess {
            emitEvent(Event.AutoLoginSuccess)
            setState(state.value.copy(route = NavigationRoute.Main))
        }.onFailure {
            emitEvent(Event.NeedLogin)
        }
    }

    private suspend fun emitEvent(event: Event) {
        _eventFlow.emit(event)
    }

    sealed class Event {
        object AutoLoginSuccess : Event()
        object NeedLogin : Event()
    }

    override val initialState: SplashState
        get() = SplashState.initial()

    override fun reduceEvent(oldState: SplashState, event: SplashEvent) {
    }
}

data class SplashState(
    var route: String,
) : MviState {
    companion object {
        fun initial() = SplashState(route = NavigationRoute.Login)
    }
}

sealed class SplashEvent : MviEvent
