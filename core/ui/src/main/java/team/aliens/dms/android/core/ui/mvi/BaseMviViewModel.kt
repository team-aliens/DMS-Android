package team.aliens.dms.android.core.ui.mvi

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.viewmodel.BaseViewModel

abstract class BaseMviViewModel<S : UiState, I : Intent, E : SideEffect>(
    initialState: S,
) : BaseViewModel() {

    private val stateChannel: Channel<I> = Channel()
    private val _stateFlow: MutableStateFlow<S> = MutableStateFlow(initialState)
    val stateFlow: StateFlow<S>
        get() = _stateFlow.asStateFlow()

    private val sideEffectChannel: Channel<E> = Channel()
    val sideEffectFlow: Flow<E>
        get() = sideEffectChannel.receiveAsFlow()

    protected val currentState: S
        get() = stateFlow.value

    init {
        stateChannel.receiveAsFlow()
            .onEach(::processIntent)
            .launchIn(viewModelScope)
    }

    fun postIntent(intent: I): Job = viewModelScope.launch { stateChannel.send(intent) }

    protected open fun processIntent(intent: I): Unit = throw NotImplementedError()

    protected fun reduce(newState: S): Boolean = _stateFlow.tryEmit(newState)

    protected fun postSideEffect(sideEffect: E): Job =
        viewModelScope.launch { sideEffectChannel.send(sideEffect) }

    override fun onCleared() {
        stateChannel.close()
    }
}
