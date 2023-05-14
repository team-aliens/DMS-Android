package team.aliens.dms_android.feature.navigator

import dagger.hilt.android.lifecycle.HiltViewModel
import team.aliens.dms_android._base.MviViewModel
import team.aliens.dms_android._base.UiEvent
import team.aliens.dms_android._base.UiState
import javax.inject.Inject

/*
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import team.aliens.dms_android.base.BaseViewModel
import team.aliens.dms_android.base.MviEvent
import team.aliens.dms_android.base.MviState
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain.usecase.auth.AutoSignInUseCase
import team.aliens.domain.usecase.auth.FetchAutoSignInOptionUseCase
import team.aliens.local_domain.entity.notice.UserVisibleInformEntity
import team.aliens.local_domain.usecase.uservisible.LocalUserVisibleInformUseCase

@HiltViewModel
class RootViewModel @Inject constructor(
    private val autoSignInUseCase: AutoSignInUseCase,
    private val fetchAutoSignInOptionUseCase: FetchAutoSignInOptionUseCase,
    private val localUserVisibleInformUseCase: LocalUserVisibleInformUseCase,
) : BaseViewModel<SplashState, SplashEvent>() {

    init {
        fetchAutoSignInOption()
    }

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    private fun fetchAutoSignInOption() {
        runBlocking {
            kotlin.runCatching {
                fetchAutoSignInOptionUseCase()
            }
        }
    }

    fun autoLogin() = viewModelScope.launch {
        kotlin.runCatching {
            autoSignInUseCase()
        }.onSuccess {
            emitEvent(
                Event.AutoLoginSuccess(
                    localUserVisibleInformUseCase.execute(Unit),
                ),
            )
            setState(state.value.copy(route = DmsRoute.Home.route))
        }.onFailure {
            emitEvent(Event.NeedLogin)
        }
    }

    private suspend fun emitEvent(event: Event) {
        _eventFlow.emit(event)
    }

    sealed class Event {
        data class AutoLoginSuccess(
            val userVisibleInformEntity: UserVisibleInformEntity,
        ) : Event()

        object NeedLogin : Event()
    }

    override val initialState: SplashState
        get() = SplashState.initial()

    override fun reduceEvent(oldState: SplashState, event: SplashEvent) {
    }
}

data class SplashState(
    var route: String,
    var userVisibleInformEntity: UserVisibleInformEntity,
) : MviState {
    companion object {
        fun initial() = SplashState(
            route = DmsRoute.Auth.SignIn,
            userVisibleInformEntity = UserVisibleInformEntity(
                mealService = false,
                noticeService = false,
                pointService = false,
                studyRoomService = false,
                remainService = false,
            ),
        )
    }
}

sealed class SplashEvent : MviEvent*/

@HiltViewModel
internal class RootViewModel @Inject constructor(
) : MviViewModel<RootState, RootEvent>(
    initialState = RootState.initial()
) {
    
}

internal data class RootState(
    val route: String,
) : UiState {
    companion object {
        fun initial() = RootState(
            route = DmsRoute.Home.Main,
        )
    }
}

internal sealed class RootEvent : UiEvent
