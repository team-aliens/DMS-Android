package team.aliens.dms.android.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

/**
 * Intent 패턴이 제거된 간소화된 ViewModel
 *
 * BaseMviViewModel과 달리 Intent 없이 직접 메서드 호출 방식을 사용합니다.
 *
 * @param S UI 상태를 나타내는 데이터 클래스 타입.
 * @param E Side Effect를 나타내는 타입. (필요 없으면 Unit 사용)
 * @param initialState ViewModel의 초기 상태.
 */
abstract class BaseStateViewModel<S, E>(initialState: S) : ViewModel() {

    private val _uiState: MutableStateFlow<S> = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    private val _effectChannel: Channel<E> = Channel(Channel.CONFLATED)
    val sideEffect: Flow<E> = _effectChannel.receiveAsFlow()

    protected fun setState(newState: (S) -> S) {
        _uiState.update(newState)
    }

    protected fun sendEffect(sideEffect: E) {
        _effectChannel.trySend(sideEffect)
    }

    override fun onCleared() {
        super.onCleared()
        _effectChannel.close()
    }
}
