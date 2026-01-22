package team.aliens.dms.android.feature.main.home.viewmodel

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
                throw it
            }
        }
    }

    private fun getLatestNotice() {
        viewModelScope.launch(Dispatchers.IO) {
            noticeRepository.fetchLatestNotice().onSuccess { latestNotice ->
                setState {
                    it.copy(latestNotice = latestNotice)
                }
            }
        }
    }

    internal fun showOutingPassDialog() {
        sendEffect(HomeSideEffect.ShowOutingPassDialog)
    }
}

internal data class HomeState(
    val myPage: MyPage = MyPage(),
    val latestNotice: LatestNotice = LatestNotice(),
)

internal sealed interface HomeSideEffect {
    data object ShowOutingPassDialog : HomeSideEffect
}
