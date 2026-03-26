package team.aliens.dms.android.feature.main.mypage.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.student.model.MyPage
import team.aliens.dms.android.data.student.repository.StudentRepository
import javax.inject.Inject

@HiltViewModel
internal class MyPageViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
) : BaseStateViewModel<MyPageState, MyPageSideEffect>(MyPageState()) {

    init {
        getMyPage()
    }

    private fun getMyPage() {
        viewModelScope.launch(Dispatchers.IO) {
            studentRepository.fetchMyPage().onSuccess { myPage ->
                setState { it.copy(myPage = myPage) }
            }.onFailure {
                sendEffect(MyPageSideEffect.FailFetchMyPage("마이페이지 정보 조회를 실패했어요"))
            }
        }
    }
}

@Immutable
internal data class MyPageState(
    val myPage: MyPage = MyPage(),
)

sealed interface MyPageSideEffect {
    data class FailFetchMyPage(val message: String) : MyPageSideEffect
}
