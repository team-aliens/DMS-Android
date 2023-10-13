package team.aliens.dms_android.app

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import team.aliens.dms.android.core.ui.viewmodel.BaseViewModel
import team.aliens.dms.android.core.ui.viewmodel.launchOnViewModelScope
import team.aliens.dms.android.domain.usecase.auth.CheckAutoSignInAvailableUseCase
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val checkAutoSignInAvailableUseCase: CheckAutoSignInAvailableUseCase,
) : BaseViewModel() {

    private var _autoSignInAvailable: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val autoSignInAvailable: StateFlow<Boolean?> = _autoSignInAvailable.asStateFlow()

    init {
        fetchWhetherAutoSignInAvailable()
    }

    private fun fetchWhetherAutoSignInAvailable() {
        launchOnViewModelScope {
            runCatching { checkAutoSignInAvailableUseCase() }
                .onSuccess { available ->
                    _autoSignInAvailable.emit(available)
                }
                .onFailure { throwable ->
                    throwable.printStackTrace()
                }
        }
    }
}
