package team.aliens.dms_android.feature.home.notice

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.MviViewModel
import team.aliens.dms_android.base.UiEvent
import team.aliens.dms_android.base.UiState
import team.aliens.domain.model.notice.FetchNoticeDetailsInput
import team.aliens.domain.model.notice.toModel
import team.aliens.domain.usecase.notice.FetchNoticeDetailsUseCase
import javax.inject.Inject

@HiltViewModel
internal class NoticeDetailsViewModel @Inject constructor(
    private val fetchNoticeDetailsUseCase: FetchNoticeDetailsUseCase,
) : MviViewModel<NoticeDetailsUiState, NoticeDetailsUiEvent>(
    initialState = NoticeDetailsUiState.initial(),
) {
    override fun updateState(event: NoticeDetailsUiEvent) {
        when (event) {
            is NoticeDetailsUiEvent.FetchNoticeDetails -> fetchNoticeDetails(event.noticeId)
        }
    }

    private fun fetchNoticeDetails(
        noticeId: UUID,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchNoticeDetailsUseCase(
                    fetchNoticeDetailsInput = FetchNoticeDetailsInput(
                        noticeId = noticeId,
                    ),
                )
            }.onSuccess {
                val notice = it.toModel()

                setState(
                    newState = uiState.value.copy(
                        title = notice.title,
                        content = notice.content!!,
                        createdAt = notice.createdAt,
                    ),
                )
            }
        }
    }
}

internal data class NoticeDetailsUiState(
    val title: String,
    val content: String,
    val createdAt: String,
) : UiState {
    companion object {
        fun initial() = NoticeDetailsUiState(
            title = "",
            content = "",
            createdAt = "",
        )
    }
}

internal sealed class NoticeDetailsUiEvent : UiEvent {
    class FetchNoticeDetails(val noticeId: UUID) : NoticeDetailsUiEvent()
}
