package team.aliens.dms_android.feature

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import team.aliens.dms_android.base.BaseViewModel
import team.aliens.dms_android.base.MviEvent
import team.aliens.dms_android.base.MviState
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain.usecase.auth.FetchAutoSignInOptionUseCase
import team.aliens.local_domain.entity.notice.UserVisibleInformEntity
import team.aliens.local_domain.usecase.uservisible.LocalUserVisibleInformUseCase
import javax.inject.Inject

@HiltViewModel
class _RootViewModel @Inject constructor(
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
/*

    fun autoLogin() = viewModelScope.launch {
        kotlin.runCatching {
            autoSignInUseCase()
        }.onSuccess {
            emitEvent(
                Event.AutoLoginSuccess(
                    localUserVisibleInformUseCase.execute(Unit),
                ),
            )
            setState(state.value.copy(route = "DUMMY"))
        }.onFailure {
            emitEvent(Event.NeedLogin)
        }
    }
*/

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
            route = "LOGIN",
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

sealed class SplashEvent : MviEvent
