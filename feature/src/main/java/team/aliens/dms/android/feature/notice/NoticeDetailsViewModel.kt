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

/*

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.feature._legacy.base.BaseMviViewModel
import team.aliens.dms.android.feature._legacy.base.MviIntent
import team.aliens.dms.android.feature._legacy.base.MviSideEffect
import team.aliens.dms.android.feature._legacy.base.MviState
import team.aliens.dms.android.domain.model.notice.FetchNoticeDetailsInput
import team.aliens.dms.android.domain.model.notice.toModel
import team.aliens.dms.android.domain.usecase.notice.FetchNoticeDetailsUseCase
import javax.inject.Inject

@HiltViewModel
internal class NoticeDetailsViewModel @Inject constructor(
    private val fetchNoticeDetailsUseCase: FetchNoticeDetailsUseCase,
) : BaseMviViewModel<NoticeDetailsIntent, NoticeDetailsState, NoticeDetailsSideEffect>(
    initialState = NoticeDetailsState.initial(),
) {
    override fun processIntent(intent: NoticeDetailsIntent) {
        when (intent) {
            is NoticeDetailsIntent.FetchNoticeDetails -> fetchNoticeDetails(intent.noticeId)
        }
    }

    private fun fetchNoticeDetails(noticeId: UUID) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchNoticeDetailsUseCase(
                    fetchNoticeDetailsInput = FetchNoticeDetailsInput(noticeId = noticeId),
                )
            }.onSuccess {
                val notice = it.toModel()

                reduce(
                    newState = stateFlow.value.copy(
                        title = notice.title,
                        content = notice.content!!,
                        createdAt = notice.createdAt,
                    ),
                )
            }
        }
    }
}

internal sealed class NoticeDetailsIntent : MviIntent {
    class FetchNoticeDetails(val noticeId: UUID) : NoticeDetailsIntent()
}

internal data class NoticeDetailsState(
    val title: String,
    val content: String,
    val createdAt: String,
) : MviState {
    companion object {
        fun initial() = NoticeDetailsState(
            title = "",
            content = "",
            createdAt = "",
        )
    }
}

internal sealed class NoticeDetailsSideEffect : MviSideEffect
*/
