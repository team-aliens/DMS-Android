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
    private val myPage: MyPage?,
) : UiState() {
    companion object {
        fun initial() = MyPageUiState(
            myPage = null,
        )
    }
}

internal sealed class MyPageIntent : Intent()

internal sealed class MyPageSideEffect : SideEffect()

/*

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.feature._legacy.base.BaseMviViewModel
import team.aliens.dms.android.feature._legacy.base.MviIntent
import team.aliens.dms.android.feature._legacy.base.MviSideEffect
import team.aliens.dms.android.feature._legacy.base.MviState
import team.aliens.dms.android.domain.model.mypage.MyPage
import team.aliens.dms.android.domain.usecase.auth.SignOutUseCase
import team.aliens.dms.android.domain.usecase.student.FetchMyPageUseCase
import team.aliens.dms.android.domain.usecase.student.WithdrawUseCase
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
*/
