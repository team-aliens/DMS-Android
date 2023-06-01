package team.aliens.dms_android.feature.notice

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android._base.MviViewModel
import team.aliens.domain._model._common.Order
import team.aliens.domain._model.notice.FetchNoticeDetailsInput
import team.aliens.domain._model.notice.FetchNoticesInput
import team.aliens.domain._model.notice.Notice
import team.aliens.domain.usecase.notice.FetchNoticeDetailsUseCase
import team.aliens.domain.usecase.notice.FetchNoticesUseCase
import team.aliens.domain.usecase.notice.FetchWhetherNewNoticesExistUseCase

@HiltViewModel
internal class NoticesViewModel @Inject constructor(
    private val fetchNoticesUseCase: FetchNoticesUseCase,
    private val fetchNoticeDetailsUseCase: FetchNoticeDetailsUseCase,
    private val fetchHasNewNoticesUseCase: FetchWhetherNewNoticesExistUseCase,
) : MviViewModel<NoticesUiState, NoticesUiEvent>(NoticesUiState.initial()) {

    init {
        fetchNotices()
    }

    override fun updateState(event: NoticesUiEvent) {
        when (event) {
            is NoticesUiEvent.FetchNotices -> {
                setOrder()
                fetchNotices()
            }

            is NoticesUiEvent.CheckHasNewNotice -> {
                fetchHasNewNotices()
            }

            is NoticesUiEvent.SetNoticeId -> {
                setNoticeId(
                    noticeId = event.noticeId,
                )
            }
        }
    }


    private fun fetchNotices() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchNoticesUseCase(
                    fetchNoticesInput = FetchNoticesInput(
                        order = uiState.value.order,
                    )
                )
            }.onSuccess {
                setState(
                    newState = uiState.value.copy(
                        notices = it.notices,
                    )
                )
            }.onFailure {
                setNoticesError(
                    error = it,
                )
            }
        }
    }

    private fun fetchNoticeDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchNoticeDetailsUseCase(
                    fetchNoticeDetailsInput = FetchNoticeDetailsInput(
                        noticeId = uiState.value.noticeId!!,
                    )
                )
            }.onSuccess {
                setState(
                    newState = uiState.value.copy(
                        notice = Notice(
                            id = it.id,
                            title = it.title,
                            content = it.content,
                            createdAt = it.createdAt,
                        ),
                    )
                )
            }.onFailure {
                setNoticesError(
                    error = it,
                )
            }
        }
    }

    private fun fetchHasNewNotices() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchHasNewNoticesUseCase()
            }.onSuccess {
                setState(
                    newState = uiState.value.copy(
                        newNoticesExists = it.newNoticesExists,
                    )
                )
            }.onFailure {
                setNoticesError(
                    error = it,
                )
            }
        }
    }

    private fun setNoticeId(
        noticeId: UUID,
    ) {
        setState(
            newState = uiState.value.copy(
                noticeId = noticeId,
            )
        )
        fetchNoticeDetails()
    }

    private fun setNoticesError(
        error: Throwable,
    ) {
        setState(
            newState = uiState.value.copy(
                error = error,
            )
        )
    }

    private fun setOrder(){
        setState(
            newState = uiState.value.copy(
                order = when (uiState.value.order) {
                    Order.NEW -> Order.OLD
                    else -> Order.NEW
                },
            )
        )
    }
}