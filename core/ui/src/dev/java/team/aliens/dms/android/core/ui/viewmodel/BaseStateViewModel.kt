package team.aliens.dms.android.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Intent 패턴이 제거된 간소화된 ViewModel
 *
 * BaseMviViewModel과 달리 Intent 없이 직접 메서드 호출 방식을 사용합니다.
 *
 * @param S UiState 타입
 * @param E SideEffect 타입 (필요 없으면 Unit 사용)
 */
abstract class BaseStateViewModel<S, E>(initialState: S) : ViewModel() {

    private val _stateFlow: MutableStateFlow<S> = MutableStateFlow(initialState)
    val stateFlow: StateFlow<S> = _stateFlow.asStateFlow()

    private val sideEffectChannel: Channel<E> = Channel(Channel.CONFLATED)
    val sideEffectFlow: Flow<E> = sideEffectChannel.receiveAsFlow()

    protected fun setState(newState: () -> S) {
        _stateFlow.update {
            newState()
        }
    }

    protected fun postSideEffect(sideEffect: E) {
        sideEffectChannel.trySend(sideEffect)
    }

    override fun onCleared() {
        super.onCleared()
        sideEffectChannel.close()
    }
}
