package team.aliens.dms_android.feature

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import team.aliens.domain.model.student.Features
import team.aliens.domain.usecase.auth.CheckAutoSignInAvailableUseCase
import team.aliens.domain.usecase.school.FetchLocalAvailableFeaturesUseCase
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val checkAutoSignInAvailableUseCase: CheckAutoSignInAvailableUseCase,
    private val fetchLocalAvailableFeaturesUseCase: FetchLocalAvailableFeaturesUseCase,
) : ViewModel() {
    fun checkAutoSignInAvailable(): Boolean {
        return runBlocking {
            kotlin.runCatching {
                checkAutoSignInAvailableUseCase()
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
