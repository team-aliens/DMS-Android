package team.aliens.dms_android.feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.domain.usecase.auth.ReissueTokenUseCase
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val reissueTokenUseCase: ReissueTokenUseCase,
) : ViewModel() {
    init {
        autoSignIn()
    }

    private val _isSignInSuccess = MutableLiveData<Boolean>()
    val isSignInSuccess: LiveData<Boolean>
        get() = _isSignInSuccess

    private fun autoSignIn() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                reissueTokenUseCase()
            }.onSuccess {
                _isSignInSuccess.postValue(true)
            }.onFailure {
                _isSignInSuccess.postValue(false)
            }
        }
    }
}
