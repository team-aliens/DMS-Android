package team.aliens.dms_android.feature.notice

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.MviViewModel
import team.aliens.dms_android.base.UiEvent
import team.aliens.dms_android.base.UiState
import team.aliens.domain.model._common.Order
import team.aliens.domain.model.notice.FetchNoticeDetailsInput
import team.aliens.domain.model.notice.FetchNoticesInput
import team.aliens.domain.model.notice.Notice
import team.aliens.domain.model.notice.toModel
import team.aliens.domain.usecase.notice.FetchNoticeDetailsUseCase
import team.aliens.domain.usecase.notice.FetchNoticesUseCase
import team.aliens.domain.usecase.notice.FetchWhetherNewNoticesExistUseCase

@HiltViewModel
internal class NoticesViewModel @Inject constructor(
    private val fetchNoticesUseCase: FetchNoticesUseCase,
    private val fetchNoticeDetailsUseCase: FetchNoticeDetailsUseCase,
    private val fetchWhetherNewNoticesExistUseCase: FetchWhetherNewNoticesExistUseCase,
) : MviViewModel<NoticesUiState, NoticesUiEvent>(NoticesUiState.initial()) {

    init {
        fetchNotices()
    }

    override fun updateState(event: NoticesUiEvent) {
        when (event) {
            is NoticesUiEvent.SetOrder -> {
                setOrder()
                fetchNotices()
            }

            is NoticesUiEvent.FetchNoticeDetails -> {
                setNoticeId(event.noticeId)
                fetchNoticeDetails()
            }

            is NoticesUiEvent.FetchWhetherNewNoticesExist -> {
                fetchWhetherNewNoticesExist()
            }
        }
    }

    private fun fetchNotices() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchNoticesUseCase(
                    fetchNoticesInput = FetchNoticesInput(
                        order = uiState.value.order,
                    ),
                )
            }.onSuccess {
                setNotices(
                    notices = it.notices.toModel()
                )
            }.onFailure {

            }
        }
    }

    private fun fetchNoticeDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchNoticeDetailsUseCase(
                    fetchNoticeDetailsInput = FetchNoticeDetailsInput(
                        noticeId = uiState.value.noticeId!!,
                    ),
                )
            }.onSuccess {
                setNoticeDetails(
                    noticeDetails = it.toModel()
                )
            }.onFailure {

            }
        }
    }

    internal fun fetchWhetherNewNoticesExist() {
        viewModelScope.launch {
            kotlin.runCatching {
                fetchWhetherNewNoticesExistUseCase()
            }.onSuccess {
                setWhetherNewNoticesExists(
                    whetherNewNoticesExists = it.newNotices,
                )
            }.onFailure {

            }
        }
    }

    private fun setNotices(
        notices: List<Notice>
    ) {
        setState(
            newState = uiState.value.copy(
                notices = notices,
            )
        )
    }

    private fun setNoticeDetails(
        noticeDetails: Notice,
    ) {
        setState(
            newState = uiState.value.copy(
                noticeDetails = noticeDetails,
            )
        )
    }

    private fun setWhetherNewNoticesExists(
        whetherNewNoticesExists: Boolean,
    ) {
        setState(
            newState = uiState.value.copy(
                whetherNewNoticesExists = whetherNewNoticesExists,
            )
        )
    }

    private fun setOrder() {
        setState(
            newState = uiState.value.copy(
                order = when (uiState.value.order) {
                    Order.NEW -> Order.OLD
                    else -> Order.NEW
                }
            )
        )
    }

    private fun setNoticeId(
        noticeId: UUID,
    ){
        setState(
            newState = uiState.value.copy(
                noticeId = noticeId,
            )
        )
    }
}

data class NoticesUiState(
    val order: Order,
    val noticeId: UUID?,
    val notices: List<Notice>,
    val noticeDetails: Notice,
    val whetherNewNoticesExists: Boolean,
) : UiState {
    companion object {
        fun initial() = NoticesUiState(
            order = Order.NEW,
            noticeId = null,
            notices = emptyList(),
            noticeDetails = Notice(
                id = null,
                title = "",
                content = "",
                createdAt = "",
            ),
            whetherNewNoticesExists = false,
        )
    }
}

sealed class NoticesUiEvent : UiEvent {
    object SetOrder : NoticesUiEvent()
    class FetchNoticeDetails(val noticeId: UUID): NoticesUiEvent()
    object FetchWhetherNewNoticesExist: NoticesUiEvent()
}