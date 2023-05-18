package team.aliens.dms_android.feature

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import team.aliens.dms_android._base.MviViewModel
import team.aliens.dms_android._base.UiEvent
import team.aliens.dms_android._base.UiState
import team.aliens.domain._model._common.toModel
import team.aliens.domain._model.student.Feature
import team.aliens.domain.usecase.auth.AutoSignInUseCase
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val autoSignInUseCase: AutoSignInUseCase,
) : MviViewModel<MainUiState, MainUiEvent>(
    initialState = MainUiState.initial(),
) {
    init {
        autoSignIn()
    }

    private fun autoSignIn() {
        runBlocking { // fixme 로직 생각해보기
            kotlin.runCatching {
                autoSignInUseCase()
            }.onSuccess {
                setState(
                    newState = uiState.value.copy(
                        autoSignInSuccess = true,
                        feature = it.features.toModel(),
                    ),
                )
            }.onFailure {
                setState(
                    newState = uiState.value.copy(
                        autoSignInSuccess = false,
                    ),
                )
            }
        }
    }
}

internal data class MainUiState(
    val autoSignInSuccess: Boolean,
    val feature: Feature,
) : UiState {
    companion object {
        fun initial() = MainUiState(
            autoSignInSuccess = false,
            feature = Feature(
                mealService = false,
                noticeService = false,
                pointService = false,
                studyRoomService = false,
                remainsService = false,
            ),
        )
    }
}

internal sealed class MainUiEvent : UiEvent
