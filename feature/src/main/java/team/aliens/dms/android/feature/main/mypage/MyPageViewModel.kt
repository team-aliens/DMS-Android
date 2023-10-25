package team.aliens.dms.android.feature.main.mypage

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.student.model.MyPage
import team.aliens.dms.android.data.student.repository.StudentRepository
import javax.inject.Inject

@HiltViewModel
internal class MyPageViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
) : BaseMviViewModel<MyPageUiState, MyPageIntent, MyPageSideEffect>(
    initialState = MyPageUiState.initial(),
) {
    init {
        fetchMyPage()
    }

    private fun fetchMyPage() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                studentRepository.fetchMyPage()
            }.onSuccess { myPage ->
                reduce(newState = stateFlow.value.copy(myPage = myPage))
            }
        }
    }
}

internal data class MyPageUiState(
    val myPage: MyPage?,
) : UiState() {
    companion object {
        fun initial() = MyPageUiState(
            myPage = null,
        )
    }
}

internal sealed class MyPageIntent : Intent() {

    data object SignOut : MyPageIntent()

    data object Withdraw : MyPageIntent()
}

internal sealed class MyPageSideEffect : SideEffect()
