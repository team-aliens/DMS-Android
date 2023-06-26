package team.aliens.dms_android.feature.main.home.notice

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.BaseMviViewModel
import team.aliens.dms_android.base.MviIntent
import team.aliens.dms_android.base.MviSideEffect
import team.aliens.dms_android.base.MviState
import team.aliens.domain.model._common.Order
import team.aliens.domain.model.notice.FetchNoticesInput
import team.aliens.domain.model.notice.Notice
import team.aliens.domain.usecase.notice.FetchNoticesUseCase
import javax.inject.Inject

@HiltViewModel
internal class NoticesViewModel @Inject constructor(
    private val fetchNoticesUseCase: FetchNoticesUseCase,
) : BaseMviViewModel<NoticesEvent, NoticesState, NoticesSideEffect>(
    initialState = NoticesState.initial(),
) {
    init {
        fetchNotices(Order.NEW)
    }

    override fun processIntent(intent: NoticesEvent) {
        when (intent) {
            is NoticesEvent.SetOrder -> fetchNotices(intent.order)
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
                reduce(
                    newState = stateFlow.value.copy(
                        order = order,
                        notices = it,
                    )
                )
            }
        }
    }
}

internal sealed class NoticesEvent : MviIntent {
    class SetOrder(val order: Order) : NoticesEvent()
}

internal data class NoticesState(
    val order: Order,
    val notices: List<Notice>,
) : MviState {
    companion object {
        fun initial() = NoticesState(
            order = Order.NEW,
            notices = emptyList(),
        )
    }
}

internal sealed class NoticesSideEffect : MviSideEffect
