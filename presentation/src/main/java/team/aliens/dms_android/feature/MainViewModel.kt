package team.aliens.dms_android.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import team.aliens.domain.usecase.auth.AutoSignInUseCase
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val autoSignInUseCase: AutoSignInUseCase,
) : ViewModel() {
    init {
        autoSignIn()
    }

    private val _isSignInSuccess = MutableStateFlow(false)
    val isSignInSuccess: StateFlow<Boolean>
        get() = _isSignInSuccess.asStateFlow()

    private fun autoSignIn() {
        viewModelScope.launch(Dispatchers.IO) {

            kotlin.runCatching {
                autoSignInUseCase()
            }.onSuccess {
                _isSignInSuccess.value = true
            }.onFailure {
                _isSignInSuccess.value = false
            }
        }
    }
}
