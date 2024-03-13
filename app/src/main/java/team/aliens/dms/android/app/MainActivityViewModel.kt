package team.aliens.dms.android.app

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import team.aliens.dms.android.core.jwt.JwtProvider
import team.aliens.dms.android.core.ui.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val jwtProvider: JwtProvider,
) : BaseViewModel() {
    val autoSignInAvailable: StateFlow<Boolean> = jwtProvider.isCachedAccessTokenAvailable
}
