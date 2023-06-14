package team.aliens.dms_android.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

internal abstract class BaseMviViewModel<I : MviIntent, S : MviState, E : MviSideEffect>(
    protected val initialState: S,
) : ViewModel() {
    private val stateChannel: Channel<I> = Channel()
    private val _stateFlow: MutableStateFlow<S> = MutableStateFlow(initialState)
    internal val stateFlow: StateFlow<S>
        get() = _stateFlow.asStateFlow()

    private val sideEffectChannel: Channel<E> = Channel()
    internal val sideEffectFlow: Flow<E>
        get() = sideEffectChannel.receiveAsFlow()

    init {
        stateChannel.receiveAsFlow()
            .onEach(::processIntent)
            .launchIn(viewModelScope)
    }

    /**
     * called by view(user), sent to view model
     */
    internal fun postIntent(intent: I) {
        viewModelScope.launch {
            stateChannel.send(intent)
        }
    }

    protected open fun processIntent(intent: I) {
        throw NotImplementedError() // need to be implemented when using this function
    }

    protected fun reduce(newState: S) {
        _stateFlow.tryEmit(newState)
    }

    /**
     * called by view model, sent to view(user)
     */
    protected fun postSideEffect(event: E) {
        viewModelScope.launch {
            sideEffectChannel.send(event)
        }
    }

    override fun onCleared() {
        stateChannel.close()
    }
}
