package team.aliens.dms_android.feature.main.home.mypage

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.BaseMviViewModel
import team.aliens.dms_android.base.MviIntent
import team.aliens.dms_android.base.MviSideEffect
import team.aliens.dms_android.base.MviState
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
) : BaseMviViewModel<MyPageIntent, MyPageState, MyPageSideEffect>(
    initialState = MyPageState.initial(),
) {
    init {
        fetchMyPage()
    }

    override fun processIntent(intent: MyPageIntent) {
        when (intent) {
            MyPageIntent.SignOut -> signOut()
            MyPageIntent.Withdraw -> withdraw()
        }
    }

    private fun fetchMyPage() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchMyPageUseCase()
            }.onSuccess { fetchedMyPage ->
                reduce(
                    newState = stateFlow.value.copy(
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

internal sealed class MyPageIntent : MviIntent {
    object SignOut : MyPageIntent()
    object Withdraw : MyPageIntent()
}

internal data class MyPageState(
    val myPage: MyPage?,
) : MviState {
    companion object {
        fun initial(): MyPageState {
            return MyPageState(
                myPage = null,
            )
        }
    }
}

internal sealed class MyPageSideEffect : MviSideEffect
