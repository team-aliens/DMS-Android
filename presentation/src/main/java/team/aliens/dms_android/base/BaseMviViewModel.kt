package team.aliens.dms_android.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

internal abstract class BaseMviViewModel<I : MviIntent, S : MviViewState, E : MviSingleEvent>(
    protected val initialState: S,
) : ViewModel() {
    private val _uiState: MutableStateFlow<S> = MutableStateFlow(initialState)
    internal val uiState: StateFlow<S>
        get() = _uiState.asStateFlow()

    private val _singleEvent: MutableSharedFlow<E> = MutableSharedFlow()
    internal val singleEvent: SharedFlow<E>
        get() = _singleEvent.asSharedFlow()

    private val eventChannel = Channel<I>()

    init {
        eventChannel.receiveAsFlow()
            .onEach(::processIntent)
            .launchIn(viewModelScope)
    }

    internal fun onIntent(intent: I) {
        viewModelScope.launch {
            eventChannel.send(intent)
        }
    }

    protected open fun processIntent(intent: I) {
        throw NotImplementedError() // need to be implemented when using this function
    }

    protected fun setState(newState: S) {
        _uiState.tryEmit(newState)
    }

    override fun onCleared() {
        super.onCleared()
        eventChannel.close()
    }
}
