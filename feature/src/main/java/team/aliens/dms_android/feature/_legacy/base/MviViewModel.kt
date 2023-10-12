package team.aliens.dms_android.feature._legacy.base

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

@Deprecated("deprecated because of wrong mvi concept")
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

    /*
    View에서 이벤트가 발생했을 때 호출되는 함수입니다
     */
    fun onEvent(event: E) {
        viewModelScope.launch {
            events.send(event)
        }
    }

    /*
    ViewModel 안의 Channel 객체의 콜백으로 등록되고 호출되며, 각 이벤트에 대한 실질적인 작업을 정의합니다
     */
    protected open fun updateState(event: E) {
        /* explicit blank */
    }

    /*
    ViewModel에 정의된 State를 지정합니다
    - 새로운 state를 받아 기존 state 대신 emit합니다
     */
    protected fun setState(newState: S) {
        _uiState.tryEmit(newState)
    }

    override fun onCleared() {
        super.onCleared()
        events.close()
    }
}
