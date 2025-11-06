package team.aliens.dms.android.feature.main.announcement

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.notice.model.Notice
import team.aliens.dms.android.data.notice.repository.NoticeRepository
import team.aliens.dms.android.shared.model.Order
import javax.inject.Inject

@HiltViewModel
internal class AnnouncementListViewModel @Inject constructor(
    private val noticeRepository: NoticeRepository,
) : BaseMviViewModel<AnnouncementListUiState, AnnouncementIntent, AnnouncementSideEffect>(
    initialState = AnnouncementListUiState.initial(),
) {
    private var noticesAscByDate: List<Notice> = emptyList()
    private var noticesDescByDate: List<Notice> = emptyList()

    init {
        fetchNotices()
    }

    override fun processIntent(intent: AnnouncementIntent) {
        when (intent) {
            is AnnouncementIntent.UpdateOrder -> updateOrder(intent.order)
        }
    }

    private fun fetchNotices() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                noticeRepository.fetchNotices(order = Order.NEW)
            }.onSuccess { notices ->
                noticesAscByDate = notices
                noticesDescByDate = notices.reversed()
                reduce(newState = stateFlow.value.copy(notices = notices))
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    private fun updateOrder(order: Order): Boolean = reduce(
        newState = stateFlow.value.copy(
            selectedOrder = order,
            notices = when (order) {
                Order.NEW -> this.noticesAscByDate
                Order.OLD -> this.noticesDescByDate
            },
        ),
    )
}

internal data class AnnouncementListUiState(
    val selectedOrder: Order,
    val notices: List<Notice>,
) : UiState() {
    companion object {
        fun initial() = AnnouncementListUiState(
            selectedOrder = Order.NEW,
            notices = emptyList(),
        )
    }
}

internal sealed class AnnouncementIntent : Intent() {
    class UpdateOrder(val order: Order) : AnnouncementIntent()
}

internal sealed class AnnouncementSideEffect : SideEffect()

/*

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.feature._legacy.base.BaseMviViewModel
import team.aliens.dms.android.feature._legacy.base.MviIntent
import team.aliens.dms.android.feature._legacy.base.MviSideEffect
import team.aliens.dms.android.feature._legacy.base.MviState
import team.aliens.dms.android.domain.model._common.Order
import team.aliens.dms.android.domain.model.notice.FetchNoticesInput
import team.aliens.dms.android.domain.model.notice.Notice
import team.aliens.dms.android.domain.usecase.notice.FetchNoticesUseCase
import javax.inject.Inject

@HiltViewModel
internal class AnnouncementsViewModel @Inject constructor(
    private val fetchNoticesUseCase: FetchNoticesUseCase,
) : BaseMviViewModel<AnnouncementsIntent, AnnouncementsState, AnnouncementsSideEffect>(
    initialState = AnnouncementsState.initial(),
) {
    init {
        fetchNotices()
    }

    override fun processIntent(intent: AnnouncementsIntent) {
        when (intent) {
            is AnnouncementsIntent.SetOrder -> setOrder(intent.order)
        }
    }

    private fun fetchNotices() {
        viewModelScope.launch(Dispatchers.IO) {
            val order = Order.NEW

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

    private fun setOrder(order: Order) {
        reduce(
            newState = stateFlow.value.copy(
                order = order,
                notices = stateFlow.value.notices.reversed(),
            )
        )
    }
}

internal sealed class AnnouncementsIntent : MviIntent {
    class SetOrder(val order: Order) : AnnouncementsIntent()
}

internal data class AnnouncementsState(
    val order: Order,
    val notices: List<Notice>,
) : MviState {
    companion object {
        fun initial() = AnnouncementsState(
            order = Order.NEW,
            notices = emptyList(),
        )
    }
}

internal sealed class AnnouncementsSideEffect : MviSideEffect
*/
