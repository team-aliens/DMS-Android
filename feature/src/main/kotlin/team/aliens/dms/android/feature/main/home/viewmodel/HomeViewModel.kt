package team.aliens.dms.android.feature.main.home.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.notice.model.LatestNotice
import team.aliens.dms.android.data.notice.repository.NoticeRepository
import team.aliens.dms.android.data.student.model.MyPage
import team.aliens.dms.android.data.student.repository.StudentRepository
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    val studentRepository: StudentRepository,
    val noticeRepository: NoticeRepository
) : BaseStateViewModel<HomeState, HomeSideEffect>(HomeState()) {

    init {
        getMyPage()
        getLatestNotice()
    }

    private fun getMyPage() {
        viewModelScope.launch(Dispatchers.IO) {
            studentRepository.fetchMyPage().onSuccess { myPage ->
                setState { it.copy(myPage = myPage) }
            }.onFailure {
               sendEffect(HomeSideEffect.FailFetchMyPage("내 정보 조회를 실패했어요"))
            }
        }
    }

    private fun getLatestNotice() {
        viewModelScope.launch(Dispatchers.IO) {
            noticeRepository.fetchLatestNotice().onSuccess { latestNotice ->
                setState {
                    it.copy(latestNotice = latestNotice)
                }
            }.onFailure {
                sendEffect(HomeSideEffect.FailFetchLatestNotice("공지를 가져오는데 실패했어요"))
            }
        }
    }

    internal fun showOutingPassDialog() {
        sendEffect(HomeSideEffect.ShowOutingPassDialog)
    }
}

@Immutable
internal data class HomeState(
    val myPage: MyPage = MyPage(),
    val latestNotice: LatestNotice = LatestNotice(),
)

internal sealed interface HomeSideEffect {
    data object ShowOutingPassDialog : HomeSideEffect

    data class FailFetchLatestNotice(val message: String) : HomeSideEffect

    data class FailFetchMyPage(val message: String) : HomeSideEffect
}
