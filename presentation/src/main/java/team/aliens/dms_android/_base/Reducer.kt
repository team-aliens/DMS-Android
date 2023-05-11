package team.aliens.dms_android._base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal abstract class Reducer<S : UiState, E : UiEvent>(
    initialState: S,
) {
    private val _uiState: MutableStateFlow<S> = MutableStateFlow(initialState)
    val uiState: StateFlow<S>
        get() = _uiState.asStateFlow()

    fun setState(newState: S) {
        _uiState.tryEmit(newState)
    }

    fun sendEvent(event: E) {
        reduce(
            oldState = _uiState.value,
            event = event,
        )
    }

    protected abstract fun reduce(
        oldState: S,
        event: E,
    )
}
