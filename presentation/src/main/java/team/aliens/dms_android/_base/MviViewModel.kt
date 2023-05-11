package team.aliens.dms_android._base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// TODO component's name must be changed to "BaseViewModel", removing old BaseViewModel(s)
internal abstract class MviViewModel<S : UiState, in E : UiEvent>(
    initialState: S,
) {
    private val _uiState: MutableStateFlow<S> = MutableStateFlow(initialState)
    val uiState: StateFlow<S>
        get() = _uiState.asStateFlow()

    protected fun setState(newState: S) {
        _uiState.tryEmit(newState)
    }

    abstract fun onEvent(event: E)
}
