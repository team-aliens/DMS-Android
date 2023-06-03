package team.aliens.dms_android.feature.notice

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.MviViewModel
import team.aliens.dms_android.base.UiEvent
import team.aliens.dms_android.base.UiState
import team.aliens.domain.model._common.Order
import team.aliens.domain.model.notice.FetchNoticesInput
import team.aliens.domain.model.notice.Notice
import team.aliens.domain.usecase.notice.FetchNoticesUseCase
import javax.inject.Inject

@HiltViewModel
internal class NoticesViewModel @Inject constructor(
    private val fetchNoticesUseCase: FetchNoticesUseCase,
) : MviViewModel<NoticesUiState, NoticesUiEvent>(
    initialState = NoticesUiState.initial(),
) {
    init {
        fetchNotices(Order.NEW)
    }

    override fun updateState(event: NoticesUiEvent) {
        when (event) {
            is NoticesUiEvent.SetOrder -> fetchNotices(event.order)
        }
    }

    private fun fetchNotices(
        order: Order,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchNoticesUseCase(
                    fetchNoticesInput = FetchNoticesInput(
                        order = order,
                    ),
                )
            }.onSuccess {
                setState(
                    newState = uiState.value.copy(
                        order = order,
                        notices = it,
                    )
                )
            }
        }
    }
}

internal data class NoticesUiState(
    val order: Order,
    val notices: List<Notice>,
) : UiState {
    companion object {
        fun initial() = NoticesUiState(
            order = Order.NEW,
            notices = emptyList(),
        )
    }
}

internal sealed class NoticesUiEvent : UiEvent {
    class SetOrder(val order: Order) : NoticesUiEvent()
}