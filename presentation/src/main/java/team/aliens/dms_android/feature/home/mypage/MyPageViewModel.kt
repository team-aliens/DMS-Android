package team.aliens.dms_android.feature.home.mypage

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.MviViewModel
import team.aliens.dms_android.base.UiEvent
import team.aliens.dms_android.base.UiState
import team.aliens.domain.model.mypage.MyPage
import team.aliens.domain.usecase.auth.SignOutUseCase
import team.aliens.domain.usecase.student.FetchMyPageUseCase
import team.aliens.domain.usecase.student.WithdrawUseCase
import javax.inject.Inject

@HiltViewModel
internal class MyPageViewModel @Inject constructor(
    private val fetchMyPageUseCase: FetchMyPageUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val withdrawUseCase: WithdrawUseCase,
) : MviViewModel<MyPageUiState, MyPageUiEvent>(
    initialState = MyPageUiState.initial(),
) {
    init {
        fetchMyPage()
    }

    override fun updateState(event: MyPageUiEvent) {
        when (event) {
            MyPageUiEvent.SignOut -> signOut()
            MyPageUiEvent.Withdraw -> withdraw()
        }
    }

    private fun fetchMyPage() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchMyPageUseCase()
            }.onSuccess { fetchedMyPage ->
                setState(
                    newState = uiState.value.copy(
                        myPage = fetchedMyPage,
                    ),
                )
            }
        }
    }

    private fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                signOutUseCase()
            }.onSuccess {

            }
        }
    }

    private fun withdraw() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                withdrawUseCase()
            }.onSuccess {

            }
        }
    }
}

internal data class MyPageUiState(
    val myPage: MyPage?,
) : UiState {
    companion object {
        fun initial(): MyPageUiState {
            return MyPageUiState(
                myPage = null,
            )
        }
    }
}

internal sealed class MyPageUiEvent : UiEvent {
    object SignOut : MyPageUiEvent()
    object Withdraw : MyPageUiEvent()
}
