package team.aliens.dms_android

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import team.aliens.domain.model.student.Features
import team.aliens.domain.usecase.auth.CheckRefreshTokenAvailableUseCase
import team.aliens.domain.usecase.school.FetchLocalAvailableFeaturesUseCase
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val checkRefreshTokenAvailableUseCase: CheckRefreshTokenAvailableUseCase,
    private val fetchLocalAvailableFeaturesUseCase: FetchLocalAvailableFeaturesUseCase,
) : ViewModel() {
    fun checkRefreshTokenAvailable(): Boolean {
        return runBlocking {
            kotlin.runCatching {
                checkRefreshTokenAvailableUseCase()
            }
        }.getOrThrow()
    }

    fun fetchLocalAvailableFeatures(): Features {
        return runBlocking {
            kotlin.runCatching {
                fetchLocalAvailableFeaturesUseCase()
            }
        }.getOrThrow()
    }
}
