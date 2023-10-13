package team.aliens.dms_android.feature.notice

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature._legacy.base.BaseMviViewModel
import team.aliens.dms_android.feature._legacy.base.MviIntent
import team.aliens.dms_android.feature._legacy.base.MviSideEffect
import team.aliens.dms_android.feature._legacy.base.MviState
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
