package team.aliens.dms.android.feature.notice

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.notice.repository.NoticeRepository
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
internal class NoticeDetailsViewModel @Inject constructor(
    private val noticeRepository: NoticeRepository,
) : BaseMviViewModel<NoticeDetailsUiState, NoticeDetailsIntent, NoticeDetailsSideEffect>(
    initialState = NoticeDetailsUiState.initial(),
) {
    override fun processIntent(intent: NoticeDetailsIntent) {
        when (intent) {
            is NoticeDetailsIntent.FetchNoticeDetails -> this.fetchNoticeDetails(intent.noticeId)
        }
    }

    private fun fetchNoticeDetails(noticeId: UUID) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                noticeRepository.fetchNoticeDetails(noticeId)
            }.onSuccess { notice ->
                reduce(
                    newState = stateFlow.value.copy(
                        title = notice.title,
                        content = notice.content,
                        createdAt = notice.createdAt,
                    ),
                )
            }
        }
    }
}

internal data class NoticeDetailsUiState(
    // TODO: notice model로 변경
    val title: String?,
    val content: String?,
    val createdAt: LocalDateTime?,
) : UiState() {
    companion object {
        fun initial() = NoticeDetailsUiState(
            title = null,
            content = null,
            createdAt = null,
        )
    }
}

internal sealed class NoticeDetailsIntent : Intent() {
    class FetchNoticeDetails(val noticeId: UUID) : NoticeDetailsIntent()
}

internal sealed class NoticeDetailsSideEffect : SideEffect()
