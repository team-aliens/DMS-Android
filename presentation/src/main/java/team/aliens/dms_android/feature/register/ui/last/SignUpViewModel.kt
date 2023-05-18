package team.aliens.dms_android.feature.register.ui.last

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature.register.event.SignUpEvent
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain._model.student.SignUpInput
import team.aliens.domain.usecase.student.SignUpUseCase
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
) : ViewModel() {
    private val _signUpEvent = MutableEventFlow<SignUpEvent>()
    val signUpViewEvent = _signUpEvent.asEventFlow()

    fun signUp(signUpInput: SignUpInput) {
        viewModelScope.launch {
            kotlin.runCatching {
                signUpUseCase(signUpInput)
            }.onSuccess {
                event(SignUpEvent.SignUpSuccess)
            }.onFailure {
                // fixme 추후에 리팩토링 필요
                when (it) {
                    else -> event(SignUpEvent.UnKnownException)
                }
            }
        }
    }

    private fun event(event: SignUpEvent) {
        viewModelScope.launch {
            _signUpEvent.emit(event)
        }
    }
}
