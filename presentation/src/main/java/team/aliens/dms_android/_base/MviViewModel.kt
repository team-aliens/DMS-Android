package team.aliens.dms_android._base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

// TODO component's name must be changed to "BaseViewModel", removing old BaseViewModel(s)
internal abstract class MviViewModel<S : UiState, in E : UiEvent>(
    initialState: S,
) : ViewModel() {

    // 화면에 표시할 모든 데이터
    private val _uiState: MutableStateFlow<S> = MutableStateFlow(initialState)
    val uiState: StateFlow<S>
        get() = _uiState.asStateFlow()

    // 동시성 문제를 해결하기 위한 이벤트 채널
    private val events = Channel<E>()

    init {
        // 뷰모델 생성시 이벤트 채널 설정
        events.receiveAsFlow()
            .onEach(::updateState)
            .launchIn(viewModelScope)
    }

    // 이벤트 발생 시 채널에 데이터 전송
    fun onEvent(event: E) {
        viewModelScope.launch {
            events.send(event)
        }
    }

    // 발생한 이벤트에 따라 수행할 작업을 처리하는 함수
    protected open fun updateState(event: E) {}

    protected fun setState(newState: S) {
        _uiState.tryEmit(newState)
    }
}
