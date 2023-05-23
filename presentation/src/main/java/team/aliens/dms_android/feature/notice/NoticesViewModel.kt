package team.aliens.dms_android.feature.notice

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import team.aliens.dms_android._base.MviViewModel
import team.aliens.dms_android._base.UiEvent
import team.aliens.dms_android._base.UiState
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain._exception.RemoteException
import team.aliens.domain._model._common.Order
import team.aliens.domain._model.notice.FetchNoticeDetailsInput
import team.aliens.domain._model.notice.FetchNoticesInput
import team.aliens.domain._model.notice.FetchNoticesOutput
import team.aliens.domain._model.notice.Notice
import team.aliens.domain.exception.BadRequestException
import team.aliens.domain.exception.ForbiddenException
import team.aliens.domain.exception.ServerException
import team.aliens.domain.exception.TooManyRequestException
import team.aliens.domain.exception.UnauthorizedException
import team.aliens.domain.usecase.notice.FetchNoticeDetailsUseCase
import team.aliens.domain.usecase.notice.FetchNoticesUseCase
import team.aliens.domain.usecase.notice.FetchWhetherNewNoticesExistUseCase
import team.aliens.presentation.R

@HiltViewModel
internal class NoticesViewModel @Inject constructor(
    private val fetchNoticesUseCase: FetchNoticesUseCase,
    private val fetchNoticeDetailsUseCase: FetchNoticeDetailsUseCase,
    private val fetchHasNewNoticesUseCase: FetchWhetherNewNoticesExistUseCase,
    private val application: Application = Application(),
) : MviViewModel<NoticesUiState, NoticesUiEvent>(NoticesUiState.initial()) {

    override fun updateState(event: NoticesUiEvent) {
        when (event) {
            is NoticesUiEvent.FetchNotices -> {
                setState(
                    newState = uiState.value.copy(
                        order = event.order,
                    )
                )
                fetchNotices()
            }

            is NoticesUiEvent.FetchNoticeDetails -> {
                setState(
                    newState = uiState.value.copy(
                        noticeId = event.noticeId,
                    )
                )
                fetchNoticeDetails()
            }

            is NoticesUiEvent.CheckHasNewNotice -> {
                checkHasNewNotice()
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
                onErrorEvent(
                    throwable = it,
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
                onErrorEvent(
                    throwable = it,
                )
            }
        }
    }

    private fun checkHasNewNotice() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchHasNewNoticesUseCase()
            }.onSuccess {
                setState(
                    newState = uiState.value.copy(
                        hasNewNotice = it.newNotices,
                    )
                )
            }.onFailure {
                onErrorEvent(
                    throwable = it,
                )
            }
        }
    }

    private fun onErrorEvent(
        throwable: Throwable,
    ) = with(application.applicationContext) {
        setNoticeErrorMessage(
            message = when (throwable) {
                is RemoteException.BadRequest -> {
                    getString(R.string.error_bad_request)
                }

                is RemoteException.Unauthorized -> {
                    getString(R.string.error_unauthorized)
                }

                is RemoteException.Forbidden -> {
                    getString(R.string.error_forbidden)
                }

                is RemoteException.NotFound -> {
                    getString(R.string.error_not_found)
                }

                is RemoteException.TooManyRequests -> {
                    getString(R.string.error_too_many_request)
                }

                else -> {
                    getString(R.string.error_internal_server)
                }
            }
        )
    }

    private fun setNoticeErrorMessage(
        message: String,
    ) {
        setState(
            newState = uiState.value.copy(
                noticeErrorMessage = message,
            )
        )
    }
}