package team.aliens.dms_android

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.runBlocking
import team.aliens.dms_android.base.MviSideEffect
import team.aliens.domain.usecase.auth.CheckRefreshTokenAvailableUseCase
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val checkRefreshTokenAvailableUseCase: CheckRefreshTokenAvailableUseCase,
) : ViewModel() {
    private val _stateFlow = MutableSharedFlow<MainSideEffect>()
    val sideEffectFlow: SharedFlow<MainSideEffect>
        get() = _stateFlow.asSharedFlow()

    init {
        autoSignIn()
    }

    private fun autoSignIn() {
        runBlocking {
            kotlin.runCatching {
                checkRefreshTokenAvailableUseCase()
            }.onSuccess { refreshTokenAvailable ->
                _stateFlow.tryEmit(
                    if (refreshTokenAvailable) MainSideEffect.RefreshTokenAvailable
                    else MainSideEffect.RefreshTokenNotAvailable,
                )
            }
        }
    }
}

internal sealed class MainSideEffect : MviSideEffect {
    object RefreshTokenAvailable : MainSideEffect()
    object RefreshTokenNotAvailable : MainSideEffect()
}
