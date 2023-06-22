package team.aliens.dms_android

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import team.aliens.domain.usecase.auth.CheckRefreshTokenAvailableUseCase
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val checkRefreshTokenAvailableUseCase: CheckRefreshTokenAvailableUseCase,
) : ViewModel() {
    fun checkRefreshTokenAvailable(): Boolean {
        return runBlocking {
            kotlin.runCatching {
                checkRefreshTokenAvailableUseCase()
            }
        }.getOrThrow()
    }
}
