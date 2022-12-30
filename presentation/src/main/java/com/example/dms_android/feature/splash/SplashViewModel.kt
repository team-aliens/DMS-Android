package com.example.dms_android.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.base.MviEvent
import com.example.dms_android.base.MviState
import com.example.dms_android.feature.navigator.NavigationRoute
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import com.example.domain.usecase.user.AutoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val autoLoginUseCase: AutoLoginUseCase
) : BaseViewModel<SplashState, SplashEvent>() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    fun autoLogin() = viewModelScope.launch {
        kotlin.runCatching {
            autoLoginUseCase.execute(Unit)
        }.onSuccess {
            emitEvent(Event.AutoLoginSuccess)
            setState(
                state.value.copy(
                    route = NavigationRoute.Main
                )
            )
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
    var route: String
): MviState {
    companion object {
        fun initial() = SplashState(
            route = NavigationRoute.Login
        )
    }
}

sealed class SplashEvent : MviEvent
