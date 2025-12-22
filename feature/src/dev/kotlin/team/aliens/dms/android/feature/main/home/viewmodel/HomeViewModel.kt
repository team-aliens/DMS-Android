package team.aliens.dms.android.feature.main.home.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.student.model.MyPage
import team.aliens.dms.android.data.student.repository.StudentRepository
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    val studentRepository: StudentRepository,
) : BaseStateViewModel<HomeState, HomeSideEffect>(HomeState()) {

    init {
        getMyPage()
    }

    private fun getMyPage() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                studentRepository.fetchMyPage()
            }.onSuccess { myPage ->
                setState { it.copy(myPage = myPage) }
            }.onFailure {
                throw it
            }
        }
    }

    internal fun showOutingPassDialog() {
        sendEffect(HomeSideEffect.ShowOutingPassDialog)
    }

    internal fun navigateToNotification() {
        sendEffect(HomeSideEffect.NavigateToNotification)
    }
}

internal data class HomeState(
    val newNoticesExist: Boolean = false,
    val myPage: MyPage = MyPage(),
)

internal sealed interface HomeSideEffect {
    data object ShowOutingPassDialog : HomeSideEffect
    data object NavigateToNotification : HomeSideEffect
}
